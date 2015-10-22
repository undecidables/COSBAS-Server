package cosbas.biometric.preprocessor;
import com.google.api.client.util.Value;
import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.helper.FaceDetector;
import cosbas.biometric.helper.ImageProcessor;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * {@author Renette}
 */
@Component
public class FacialProcessing implements BiometricsPreprocessor {

    private final String encoding = ".png";
    @Value("${faces.imageSize}")
    private int dimesions = 150;
    @Autowired
    private ImageProcessor<opencv_core.Mat> helper;

    @Autowired
    private FaceDetector detector;


    /**
     * Gets the most center face from the image, scaled to the correct dimesions
     * @param data image
     * @param type The BiometricData type to be created
     * @return
     */
    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        byte[] face = detector.getCenterFace(data, encoding);
        return new BiometricData(type, face);
    }

    /**
     * Scales the
     * @param data
     * @param type
     * @return
     */
    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {

        opencv_core.Mat greyscale = helper.grayScalefromBytes(data);
        opencv_core.Mat resized = helper.scaleImage(greyscale, dimesions, dimesions);
        byte[] newData = helper.toBytes(resized, encoding);

        return new BiometricData(type, newData);
    }


}
