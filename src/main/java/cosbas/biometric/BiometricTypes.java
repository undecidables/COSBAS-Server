package cosbas.biometric;

import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.CodeValidator;
import cosbas.biometric.validators.FaceValidator;
import cosbas.biometric.validators.FingerprintValidator;

/**
 * {@author Renette Ros}
 * This class is a container for the biometric typt string constants.
 *
 */
public enum BiometricTypes {
    CODE (CodeValidator.class),
    FACE (FaceValidator.class),
    FINGER (FingerprintValidator.class);

    public final Class validatorClass;
    <T extends AccessValidator> BiometricTypes(Class<T> validatorClass) {
        this.validatorClass = validatorClass;
    }

    /**
     * Wrapper for valueOf that first makes the value uppercase.
     * @param value The value to be converted to a Biometric Type
     * @return Biometric Type for the string
     */
    public static BiometricTypes fromString(String value) {
        return BiometricTypes.valueOf(value.toUpperCase());
    }
}
