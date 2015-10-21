package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_core.cvReleaseData;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

/**
 * {@author Renette}
 */
@Component
public class FacialProcessing implements BiometricsPreprocessor {
    private int scaledFaceSize = 150;
    private final String encoding_format = ".png";

    opencv_core.IplImage grayscaleIpl(byte[] b) {
        return cvDecodeImage(cvMat(1, b.length, CV_8UC1, new BytePointer(b)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    opencv_core.Mat byteArrayToGrayscaleMat(byte[] b) throws IOException {
        return new opencv_core.Mat(grayscaleIpl(b));
    }

    byte[] matToByteAray(opencv_core.Mat mat) {

        opencv_core.CvMat m = cvEncodeImage(encoding_format, mat.asCvMat());
        BytePointer bytePointer = m.data_ptr();

        byte[] imageData = new byte[m.size()];
        bytePointer.get(imageData, 0, m.size());
        cvReleaseData(m);

        return imageData;
    }


    byte[] iplToByteArray(opencv_core.IplImage image) {
        return matToByteAray(new opencv_core.Mat(image));
    }

    private opencv_core.IplImage resizeImage(opencv_core.IplImage original) {
        opencv_core.IplImage resizedImage = opencv_core.IplImage.create(scaledFaceSize, scaledFaceSize, original.depth(), original.nChannels());
        cvResize(original, resizedImage);
        return resizedImage;
    }

    @Override
    public BiometricData processAccess(byte[] data, BiometricTypes type) {
        return new BiometricData(type, data);
    }

    @Override
    public BiometricData processRegister(byte[] data, BiometricTypes type) {
        opencv_core.IplImage iplImage = grayscaleIpl(data);

        opencv_core.IplImage resizedImage = resizeImage(iplImage);

        byte[] newData = iplToByteArray(resizedImage);

        return new BiometricData(type, newData);
    }


}
