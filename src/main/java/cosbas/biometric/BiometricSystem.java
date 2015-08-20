package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.data.BiometricUser;
import cosbas.biometric.request.AccessRecordDAO;
import cosbas.biometric.request.AccessRequest;
import cosbas.biometric.request.AccessResponse;
import cosbas.biometric.validators.AccessValidator;
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

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     *
     * @param repository The repository to be injected.
     */
    public void setAccessRecordRepository(AccessRecordDAO repository) {
        this.accessRecordRepository = repository;
    }

    public void setPersonRepository(BiometricDataDAO repository) {
        this.PersonRepository = repository;
    }


    public AccessResponse requestAccess(AccessRequest req) {
        //Create correct type of access validator from request
        //Read from config file or something?
        //Validate
        try {
        Boolean response = true;
        List<BiometricData> datas = req.getData();
        for (BiometricData data : datas) {
            AccessValidator validator = factory.getValidator(data.getType());

            validator.validate(data, req.getAction());
        }
        } catch (ValidationException e) {
            return AccessResponse.getFailureResponse(req, e.getMessage());
        }
        //TODO : make sure to get  userId from validator
        return  AccessResponse.getSuccessResponse(req, "Welcome", "u00000000");
    }

    public Boolean addUser(BiometricUser user) {
        //Add info to db
        return false;
    }

    public Boolean removeUser(String id) {
        //Remove info from db
        return false;
    }
}
