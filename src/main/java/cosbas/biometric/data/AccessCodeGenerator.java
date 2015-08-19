package cosbas.biometric.data;

/**
 * @author Renette
 * The abstract strategy for generating a new acess code.
 */
public interface AccessCodeGenerator {
    /**
     * @return A new Access Code
     */
    byte[] newAccessCode();
}
