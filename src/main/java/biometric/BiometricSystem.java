package biometric;

/**
 * Created by Renette on 2015-06-26.
 * Modules handles core biometric functions.
 */
public class BiometricSystem {
    AccessResponse requestAccess(AccessRequest req) {
        //Create correct type of access validor from request
        //Read from config file or something?
        //Validate

        return new AccessResponse(req, false, "This has not been implemented yet.");
    }

    Boolean addUser(BiometricUser user) {
        //Add info to db
        return false;
    }

    Boolean removeUser(String id) {
        //Remove info from db
        return false;
    }
}
