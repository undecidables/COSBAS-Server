package cosbas.biometric.validators;

/**
 * @author Renette
 */
public class UserNotFoundException extends Exception {
    UserNotFoundException() {super("User does not have access");}
    UserNotFoundException(String m) {super(m);}
}
