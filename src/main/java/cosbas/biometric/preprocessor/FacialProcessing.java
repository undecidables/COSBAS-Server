package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.stereotype.Component;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.cvDecodeImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

/**
 * {@author Renette}
 */
@Component
public class FacialProcessing implements BiometricsPreprocessor {
    private int imageSize = 150;

    private opencv_core.IplImage createIPL(byte[] b) {
        return cvDecodeImage(cvMat(1, b.length, CV_8UC1, new BytePointer(b)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    private byte[] iplToByteArray(opencv_core.IplImage image) {
        opencv_core.Mat mat = new opencv_core.Mat(image);
        return mat.data().asBuffer().array();
    }

    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        return new BiometricData(type, data);
    }

    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {
        opencv_core.IplImage iplImage = createIPL(data);

        opencv_core.IplImage resizedImage = resizeImage(iplImage);

        byte[] newData = iplToByteArray(resizedImage);

        return new BiometricData(type, newData);
    }

    private opencv_core.IplImage resizeImage(opencv_core.IplImage original) {
        opencv_core.IplImage resizedImage = opencv_core.IplImage.create(imageSize, imageSize, original.depth(), original.nChannels());
        cvResize(original, resizedImage);
        return resizedImage;
    }
}
