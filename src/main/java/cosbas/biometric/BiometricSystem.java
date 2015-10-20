package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterRequestDAO;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.user.User;
import cosbas.user.UserDAO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * {@author Renette Ros}
 * Service that handles core biometric functions.
 */
@Service
public class BiometricSystem {


    private RegisterRequestDAO registerRepository;
    private BiometricDataDAO biometricDataRepository;
    private ValidatorFactory factory;
    private UserDAO userRepository;


    @Autowired
    public void setRegisterRepository(RegisterRequestDAO registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Autowired
    public void setUserRepository(UserDAO userRepository) {
        this.userRepository = userRepository;
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
            DoorActions requestAction = req.getAction();

            if (dataList == null || dataList.size() == 0) {
                throw new ValidationException("No data received.");
            }

            ValidationResponse response = validate(requestAction, dataList.get(0));

            if (!response.approved) {
                return AccessResponse.getFailureResponse(req, response.data);
            }

            String user = response.data;

            if (dataList.size() > 1) {
                List<BiometricData> subList = dataList.subList(1, dataList.size());

                for (BiometricData data : subList) {
                    response = validate(requestAction, data);
                    if (!response.approved) {
                        return AccessResponse.getFailureResponse(req, response.data);
                    }

                    if (!user.equals(response.data)) {
                        return AccessResponse.getFailureResponse(req, "Error: Multiple users identified.");
                    }
                }
            }
            return AccessResponse.getSuccessResponse(req, "Welcome", user);

        } catch (ValidationException e) {
            return AccessResponse.getFailureResponse(req, e.getMessage());
        }
    }

    private ValidationResponse validate(DoorActions requestAction, BiometricData data) throws ValidationException {
        return factory.getValidator(data.getType()).validate(data, requestAction);
    }

    public User approveUser(String userID) throws BiometricTypeException {
        /**
         * Fetch al data from DB
         * Generate and save AccessCode
         * Fore each BiometricData item
         *      Get correct validator
         *      Call validator.register to do additional stuff
         * Save to db
         */
       RegisterRequest req = registerRepository.findByUserID(userID);
       if (req == null)
           throw new NullArgumentException("No registrations request for this user.");
       List<BiometricData> dataCollections = req.getData();
       for (BiometricData d : dataCollections) {
           AccessValidator v = factory.getValidator(d.getType());
           v.registerUser(d, userID);
       }

       biometricDataRepository.save(req.getData());

       User u = userRepository.findOne(userID);

       if (u == null)
           u = new User(userID, req.getContactDetails());
       else
            u.addContactDetails(req.getContactDetails());

       userRepository.save(u);

        return u;
    }

    public void deleteRegistrationRequest(String userID) {
        registerRepository.delete(userID);
    }

    public void removeUser(String id) throws BiometricTypeException {
        userRepository.delete(id);
        List<BiometricData> dataCollections = biometricDataRepository.deleteByUserID(id);
        for (BiometricData d : dataCollections) {
            AccessValidator v = factory.getValidator(d.getType());
            v.deregisterUser(d);
        }
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Iterable<RegisterRequest> getRegisterRequests() { return registerRepository.findAll(); }

    /**
     * Saves the record to the database.
     * @pre The request should contain data or contact details
     * @post If a record for that user already exist it should be merged
     * @post  The request should be saved to the database
     *
     * @param request Registration Request as parsed from HTTP request.
     * @return A register response object
     */
    public RegisterResponse register(RegisterRequest request) {
        if (request == null
                || ( CollectionUtils.isEmpty(request.getData())
                && CollectionUtils.isEmpty(request.getContactDetails()) ))
           return RegisterResponse.getFailureResponse(request, "No data or contact details in registration request.");

        String userID = request.getUserID();
        RegisterRequest existingUser = registerRepository.findByUserID(userID);
        if (existingUser != null) {
            existingUser.merge(request);
            registerRepository.save(existingUser);
            return RegisterResponse.getSuccessResponse(request, "Request merged with existing pending request.");
        } else {
            registerRepository.save(request);
            return RegisterResponse.getSuccessResponse(request, "Request pending admin Approval.");
        }
    }


}
