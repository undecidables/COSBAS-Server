package cosbas.biometric;

import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.AccessRecordDAO;
import cosbas.biometric.request.AccessResponse;
import cosbas.biometric.data.BiometricUser;
import cosbas.biometric.request.AccessRequest;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.validation.Validator;

/**
 * @author Renette
 * Modules handles core biometric functions.
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

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
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
       //AccessValidator validator = factory.getValidator(req.type);

        return new AccessResponse(req, false, "This has not been implemented yet.");
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
