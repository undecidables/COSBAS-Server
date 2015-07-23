package biometric;

/**
 * @author Renette
 * Modules handles core biometric functions.
 */
public class BiometricSystem {
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
