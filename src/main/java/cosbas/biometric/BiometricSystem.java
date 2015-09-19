package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterRecord;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.RegistrationResponse;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.RegistrationException;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.user.ContactDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@author Renette Ros}
 * Service that handles core biometric functions.
 */
@Service
public class BiometricSystem {

    static final String PENDING_COLLECTION = "PENDING";
    private MongoTemplate tempCollection;
    private BiometricDataDAO biometricDataRepository;
    private ValidatorFactory factory;

    @Autowired
    public void setTempCollection(MongoTemplate tempCollection) {
        this.tempCollection = tempCollection;
    }

    @Autowired
    public void setFactory(ValidatorFactory factory) {
        this.factory = factory;
    }

    @Autowired
    public void setBiometricDataRepository(BiometricDataDAO repository) {
        this.biometricDataRepository = repository;
    }

    /**
     * Validates an access request and identifies the person.
     *
     * @param req Access Request as parsed from HTTP request.
     * @return An access response object with either the identified user or a failure message.
     */
    public AccessResponse requestAccess(AccessRequest req) {
        try {
            List<BiometricData> dataList = req.getData();
            ValidationResponse response = validate(req, dataList.get(0));

            if (!response.approved) {
                return AccessResponse.getFailureResponse(req, response.data);
            }

            String user = response.data;
            List<BiometricData> subList = dataList.subList(1, dataList.size());

            for (BiometricData data : subList) {
                response = validate(req, data);
                if (!response.approved) {
                    return AccessResponse.getFailureResponse(req, response.data);
                }

                if (!user.equals(response.data)) {
                    return AccessResponse.getFailureResponse(req, "Error: Multiple users identified.");
                }
            }

            return AccessResponse.getSuccessResponse(req, "Welcome", user);

        } catch (ValidationException e) {
            return AccessResponse.getFailureResponse(req, e.getMessage());
        }
    }

    private ValidationResponse validate(AccessRequest req, BiometricData data) throws ValidationException {
        return factory.getValidator(data.getType()).validate(data, req.getAction());
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

    /**
     * Validates a registration request and identifies the person.
     *
     * @param req Registration Request as parsed from HTTP request.
     * @return A register response object
     */
    public RegisterResponse tryRegister(RegisterRequest req) {
        /**
         *
         */

        try {
            List<BiometricData> dataList = req.getData();
            RegistrationResponse response = register(req, dataList);

            return RegisterResponse.getSuccessResponse(req, "Your request is Pending. An administrator wll approve it soon.", req.getPersonID());

        } catch (RegistrationException e) {
            return RegisterResponse.getFailureResponse(req, e.getMessage());
        }
    }

    /**
     * Registers Biometric data on either the main collection or a temporary collection (first time registration)
     *
     * @param req  Registration Request as parsed from HTTP request.
     * @param data The actual biometric data to persist on the database
     * @return A RegistrationResponse response object
     */
    private RegistrationResponse register(RegisterRequest req, List<BiometricData> data) throws RegistrationException {

        BiometricData personInfo = biometricDataRepository.findById(req.getPersonID());

        if (personInfo == null && data != null) {
            addUser(req.getContactDetails(), req.getPersonID(), data);
            return RegistrationResponse.successfulRegistration(true, req.getPersonID());
        }
        if (data == null) {
            System.out.println("Data null");
            return null;
        }
        biometricDataRepository.save(data);
        return null;

    }

    /**
     * Registers a new user in a temporary collection on the database
     *
     * @param details  The user's contact details
     * @param userID The user's EMPLID
     * @param data   The actual biometric data to persist on the database
     * @return True
     */
    public Boolean addUser(List<ContactDetail> details, String userID, List<BiometricData> data) {
        if (!tempCollection.collectionExists(PENDING_COLLECTION))
            tempCollection.createCollection(PENDING_COLLECTION);
        RegisterRequest tmpRecord = new RegisterRequest(details, userID, data);
        tempCollection.save(tmpRecord, PENDING_COLLECTION);
        return true;
    }


}
