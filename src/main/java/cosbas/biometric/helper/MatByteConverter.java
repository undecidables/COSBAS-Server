package cosbas.biometric.helper;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.stereotype.Component;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_core.cvReleaseData;

import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.cvDecodeImage;
import static org.bytedeco.javacpp.opencv_highgui.cvEncodeImage;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

/**
 * @author Renette
 * Convertes data between byte[] and openCV mat
 */
@Component
public class MatByteConverter implements ByteImageConverter<opencv_core.Mat> {

    @Override
    public opencv_core.Mat createGrayScaleImage(byte[] data) {
        return new opencv_core.Mat(cvDecodeImage(cvMat(1, data.length, CV_8UC1, new BytePointer(data)), CV_LOAD_IMAGE_GRAYSCALE));
    }

    @Override
    public byte[] encodeBinary(opencv_core.Mat mat, String format) {

        if (!format.startsWith(".")) format = "." + format;

        opencv_core.CvMat m = cvEncodeImage(format, mat.asCvMat());
        BytePointer bytePointer = m.data_ptr();

        byte[] imageData = new byte[m.size()];
        bytePointer.get(imageData, 0, m.size());
        cvReleaseData(m);

        return imageData;
    }

    @Override
    public opencv_core.Mat scaleImage(opencv_core.Mat original, int width, int height) {
        opencv_core.Mat resizedImage = new opencv_core.Mat();
        opencv_core.Size sz = new opencv_core.Size(width, height);
        resize(original, resizedImage, sz);
        return resizedImage;
    }
}
