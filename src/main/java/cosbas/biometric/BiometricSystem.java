package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.data.BiometricUser;
import cosbas.biometric.request.AccessRecordDAO;
import cosbas.biometric.request.AccessRequest;
import cosbas.biometric.request.AccessResponse;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Renette
 *         Modules handles core biometric functions.
 */
@Service
public class BiometricSystem {

    /**
     * The database adapter/repository to use.
     */
    @Autowired
    private AccessRecordDAO accessRecordRepository;

    @Autowired
    private BiometricDataDAO PersonRepository;

    @Autowired
    private ValidatorFactory factory;

    public void setFactory(ValidatorFactory factory) {
        this.factory = factory;
    }

    public void setAccessRecordRepository(AccessRecordDAO repository) {
        this.accessRecordRepository = repository;
    }

    public void setPersonRepository(BiometricDataDAO repository) {
        this.PersonRepository = repository;
    }


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

    public Boolean addUser(BiometricUser user) {
        //Add info to db
        return false;
    }

    public Boolean approveUser(String userID) {
        return false;
    }

    public Boolean removeUser(String id) {
        //Remove info from db
        return false;
    }
}
