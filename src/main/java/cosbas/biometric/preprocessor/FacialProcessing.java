package cosbas.biometric.preprocessor;

/**
 * {@author Renette}
 */
public class FacialProcessing implements BiometricsPreprocessor {
    @Override
    public byte[] processAccess(byte[] data) {
        return data;
    }

    @Override
    public byte[] processRegister(byte[] data) {
        return data;
    }
}
