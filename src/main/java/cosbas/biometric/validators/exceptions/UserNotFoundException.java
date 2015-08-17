package cosbas.biometric.validators.exceptions;

/**
 * @author Renette
 */
public class UserNotFoundException extends ValidationException {
    public UserNotFoundException() {super("User does not have access.");}
    UserNotFoundException(String m) {super(m);}
}
