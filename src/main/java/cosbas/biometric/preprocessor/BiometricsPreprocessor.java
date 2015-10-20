package cosbas.biometric.preprocessor;

/**
 * {@author Reneette}
 */
public interface BiometricsPreprocessor {
    byte[] processAccess(byte[] data);
    byte[] processRegister(byte[] data);
}
