package cosbas.biometric.validators;

/**
 * @author Renette
 * This class is a container for the biometric typt string constants
 */
public enum BiometricTypes {
    CODE (CodeValidator.class),
    FACE (FaceValidator.class),
    FINGER (FingerprintValidator.class);

    public final Class validatorClass;
    BiometricTypes(Class validatorClass) {
        this.validatorClass = validatorClass;
    }

}
