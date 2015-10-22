package cosbas.biometric.preprocessor;

import org.springframework.stereotype.Component;

/**
 * {@author Renette}
 */
@Component
public class NoProcessing implements BiometricsPreprocessor {
    @Override
    public byte[] process(byte[] data) {
        return data;
    }
}
