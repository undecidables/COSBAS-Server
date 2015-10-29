package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.fingerprint.FingerprintTemplateCreator;
import cosbas.biometric.validators.fingerprint.FingerprintTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@author Renette Ros & Vivian Venter}
 */
@Component
public class FingerProcessing implements BiometricsPreprocessor {

    @Autowired
    private BiometricDataDAO fingerprintRepository;

    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        return new BiometricData(type, data);
    }

    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {
        try {
            FingerprintTemplateCreator creator = new FingerprintTemplateCreator();

            InputStream in = new ByteArrayInputStream(data);
            BufferedImage originalImage = getImage(in);

            FingerprintTemplateData registration = creator.createTemplateFingerprintData(null, originalImage, true, fingerprintRepository);

            return registration;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BiometricData(type, data);
    }

    private BufferedImage getImage(InputStream in) throws IOException {
        return ImageIO.read(in);
    }
}
