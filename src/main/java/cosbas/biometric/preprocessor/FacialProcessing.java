package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.cvDecodeImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

/**
 * {@author Renette}
 */
@Component
public class FacialProcessing implements BiometricsPreprocessor {
    private int scaledFaceSize = 150;

    private opencv_core.IplImage createIPl(byte[] b) {
        return cvDecodeImage(cvMat(1, b.length, CV_8UC1, new BytePointer(b)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    private BufferedImage byteArrayToImage(byte[] b) throws IOException {
        InputStream in = new ByteArrayInputStream(b);
        return ImageIO.read(in);
    }

    private opencv_core.Mat byteArrayToMat(byte[] b) throws IOException {
        BufferedImage img = byteArrayToImage(b);

        int rows = img.getWidth();
        int cols = img.getHeight();
        int type = opencv_core.CV_8UC3;
        opencv_core.Mat mat = new opencv_core.Mat(rows, cols, type);
        mat.data(new BytePointer(b));
        return mat;
    }

    private opencv_core.Mat grayscaleMat(opencv_core.Mat m) {
        opencv_core.Mat newMat = new opencv_core.Mat(m.rows(), m.cols(), CV_8UC1);
        cvtColor(m, newMat, opencv_imgproc.COLOR_BGR2GRAY);

        return newMat;
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
        opencv_core.IplImage iplImage = createIPl(data);

        opencv_core.IplImage resizedImage = resizeImage(iplImage);

        byte[] newData = iplToByteArray(resizedImage);

        return new BiometricData(type, newData);
    }

    private opencv_core.IplImage resizeImage(opencv_core.IplImage original) {
        opencv_core.IplImage resizedImage = opencv_core.IplImage.create(scaledFaceSize, scaledFaceSize, original.depth(), original.nChannels());
        cvResize(original, resizedImage);
        return resizedImage;
    }
}
