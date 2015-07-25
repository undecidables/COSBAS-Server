package biometric;

import biometric.request.AccessResponse;
import biometric.person.BiometricUser;
import biometric.person.PersonDBAdapter;
import biometric.request.AccessDBAdapter;
import biometric.request.AccessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AccessDBAdapter AccessRepository;

    @Autowired
    private PersonDBAdapter PersonRepository;


    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The repository to be injected.
     */
    public void setRepository(AccessDBAdapter repository) {
        this.AccessRepository = repository;
    }

    public void setRepository(PersonDBAdapter repository) {
        this.PersonRepository = repository;
    }


   public AccessResponse requestAccess(AccessRequest req) {
        //Create correct type of access validator from request
        //Read from config file or something?
        //Validate

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
