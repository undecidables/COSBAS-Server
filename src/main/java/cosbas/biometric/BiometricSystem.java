package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@author Renette Ros}
 *  Service that handles core biometric functions.
 */
@Service
public class BiometricSystem {

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

    public Boolean addUser(String userID, List<BiometricData> data) {
        /**
         * Save all info in a temporary collection
         */
        //Add info to db
        return false;
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
