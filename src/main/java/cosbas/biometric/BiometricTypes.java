package cosbas.biometric;

import cosbas.biometric.preprocessor.BiometricsPreprocessor;
import cosbas.biometric.preprocessor.FacialProcessing;
import cosbas.biometric.preprocessor.NoProcessing;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.CodeValidator;
import cosbas.biometric.validators.FaceValidator;
import cosbas.biometric.validators.FingerprintValidator;
import cosbas.biometric.validators.exceptions.BiometricTypeException;

/**
 * {@author Renette Ros}
 * This enum defines the biometric types recognized by the server.
 *
 */
public enum BiometricTypes {
    CODE(CodeValidator.class, NoProcessing.class),
    FACE(FaceValidator.class, FacialProcessing.class),
    FINGER(FingerprintValidator.class, NoProcessing.class);

    public final Class<? extends AccessValidator> validatorClass;
    public final Class<? extends BiometricsPreprocessor> preprocessorClass;

    BiometricTypes(Class<? extends AccessValidator> validatorClass, Class<? extends BiometricsPreprocessor> preprocessorClass) {
        this.validatorClass = validatorClass;
        this.preprocessorClass = preprocessorClass;
    }

    /**
     * Wrapper for valueOf that first makes the value uppercase.
     * @param value The value to be converted to a Biometric Type
     * @return Biometric Type for the string
     */
    public static BiometricTypes fromString(String value) throws BiometricTypeException {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BiometricTypeException("Unknown biometric type " + value);
        }
    }
}
