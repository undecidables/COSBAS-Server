package cosbas.biometric.preprocessor;

import org.springframework.stereotype.Component;

/**
 * {@author Reneette}
 */

public interface BiometricsPreprocessor {
    byte[] process(byte[] data);
}
