package cosbas.biometric.validators;

/**
 * @author Renette
 * This class is a container for the biometric typt string constants.
 *
 */
public enum BiometricTypes {
    CODE (CodeValidator.class),
    FACE (FaceValidator.class),
    FINGER (FingerprintValidator.class);

    public final Class validatorClass;
    BiometricTypes(Class validatorClass) {
        this.validatorClass = validatorClass;
    }

    /**
     * Wrapper for valueOf that first makes the value uppercase.
     * @param value The value to be converted to a Biometric Type
     * @return Biometric Type for the string
     */
    public static BiometricTypes getValue(String value) {
        return BiometricTypes.valueOf(value.toUpperCase());
    }

}
