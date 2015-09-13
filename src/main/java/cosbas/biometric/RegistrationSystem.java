package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.registration.RegisterRecord;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.RegistrationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tienie on 13/09/2015.
 */
@Service
public class RegistrationSystem {

        @Autowired
        MongoTemplate tempCollection;

        @Autowired
        private BiometricDataDAO biometricDataRepository;

        @Autowired
        private ValidatorFactory factory;

        public void setFactory(ValidatorFactory factory) {
            this.factory = factory;
        }

        public void setBiometricDataRepository(BiometricDataDAO repository) {
            this.biometricDataRepository = repository;
        }


        /**
         * Validates an access request and identifies the person.
         * @param req Access Request as parsed from HTTP request.
         * @return An access response object with either the identified user or a failure message.
         */
        public RegisterResponse tryRegister(RegisterRequest req) {
            try {
                List<BiometricData> dataList = req.getData();
                RegistrationResponse response = register(req, dataList.get(0));

                if (!response.approved) {
                    return RegisterResponse.getFailureResponse(req, response.data);
                }

                String user = response.data;
                List<BiometricData> subList = dataList.subList(1, dataList.size());

                for (BiometricData data : subList) {
                    response = register(req, data);
                    if (!response.approved) {
                        return RegisterResponse.getFailureResponse(req, response.data);
                    }

                    if (!user.equals(response.data)) {
                        return RegisterResponse.getFailureResponse(req, "Error: Multiple users identified.");
                    }
                }

                return RegisterResponse.getSuccessResponse(req, "Welcome", user);

            } catch (RegistrationException e) {
                return RegisterResponse.getFailureResponse(req, e.getMessage());
            }
        }

        private RegistrationResponse register(RegisterRequest req, BiometricData data) throws RegistrationException {

            //return factory.getValidator(data.getType()).validate(data, req.getAction());
            System.out.println(req.getPersonID());
            BiometricData personInfo = biometricDataRepository.findById(req.getPersonID());

            if (personInfo == null && data != null) {
                System.out.println("Person doesn't exist on the DB - registering.");
                addUser(req.getEmail(), req.getPersonID(), data);
                return RegistrationResponse.successfulRegistration(true, req.getPersonID());
            }
            if (data == null) {
                System.out.println("Data null");
                return null;
            }
            biometricDataRepository.save(data);
            System.out.println("Successful register");
            return null;

        }

        public Boolean addUser(String email, String userID, BiometricData data) {
            /**
             * Save all info in a temporary collection
             */
            //Add info to db
            if(!tempCollection.collectionExists("PENDING"))
                tempCollection.createCollection("PENDING");
            RegisterRecord tmpRecord = new RegisterRecord(email, userID, data);
            tempCollection.save(tmpRecord, "PENDING");
            return true;
        }

        public Boolean approveUser(String userID) {
            /**
             * Fetch al data from DB
             * Generate and save AccessCode
             * Fore each BiometricData item
             *      Get correct validator
             *      Call validator.register to save & do additional stuff
             */
            return false;
        }

        public Boolean removeUser(String id) {
            //Remove info from db
            return false;
        }
}


