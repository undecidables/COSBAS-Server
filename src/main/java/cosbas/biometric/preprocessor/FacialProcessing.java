package cosbas.biometric.preprocessor;
import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.helper.ByteImageConverter;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * {@author Renette}
 */
@Component
public class FacialProcessing implements BiometricsPreprocessor {

    private final String encoding = ".png";
    private final int dimesions = 150;
    @Autowired
    private ByteImageConverter<opencv_core.Mat> helper;

    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        opencv_core.Mat greyscale = helper.createGrayScaleImage(data);
        opencv_core.Mat resized = helper.scaleImage(greyscale, dimesions, dimesions);

        byte[] newData = helper.encodeBinary(resized, encoding);

        return new BiometricData(type, newData);
    }

    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {

        opencv_core.Mat greyscale = helper.createGrayScaleImage(data);
        opencv_core.Mat resized = helper.scaleImage(greyscale, dimesions, dimesions);

        byte[] newData = helper.encodeBinary(resized, encoding);

        return new BiometricData(type, newData);
    }


}
