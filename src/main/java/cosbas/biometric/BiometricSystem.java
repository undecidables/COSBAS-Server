package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterRequestDAO;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public void setRegisterRepository(RegisterRequestDAO registerRepository) {
        this.registerRepository = registerRepository;
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
        //Remove user info + biometric data from db
        return false;
    }

    /**
     * Validates a registration request and identifies the person.
     *
     * @param request Registration Request as parsed from HTTP request.
     * @return A register response object
     */
    public RegisterResponse register(RegisterRequest request) {
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
