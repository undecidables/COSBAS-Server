package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;

/**
 * {@author Reneette}
 */
public interface BiometricsPreprocessor {
    BiometricData processAccess(byte[] data, BiometricTypes type);
    BiometricData processRegister(byte[] data, BiometricTypes type);
}
