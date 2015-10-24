package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import org.springframework.stereotype.Component;

/**
 * {@author Renette}
 */
@Component
public class NoProcessing implements BiometricsPreprocessor {
    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        return new BiometricData(type, data);
    }

    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {
        return new BiometricData(type, data);
    }
}
