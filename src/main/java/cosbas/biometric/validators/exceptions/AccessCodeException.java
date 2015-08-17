package cosbas.biometric.validators.exceptions;

/**
 * @author Renette
 */
public class AccessCodeException extends ValidationException {
    public AccessCodeException() { super("Access code invalid, expired or not yet valid.");}
}
