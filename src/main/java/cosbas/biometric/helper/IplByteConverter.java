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
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

/**
 * @author Renette
 * Conversion between openCV's IPLImage and byte[]
 */
@Component
public class IplByteConverter implements ByteImageConverter<opencv_core.IplImage> {
    @Override
    public opencv_core.IplImage createGrayScaleImage(byte[] data) {
        return cvDecodeImage(cvMat(1, data.length, CV_8UC1, new BytePointer(data)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    @Override
    public byte[] encodeBinary(opencv_core.IplImage image, String format) {
        if (!format.startsWith(".")) format = "." + format;

        opencv_core.CvMat m = cvEncodeImage(format, image.asCvMat());
        BytePointer bytePointer = m.data_ptr();

        byte[] imageData = new byte[m.size()];
        bytePointer.get(imageData, 0, m.size());
        cvReleaseData(m);

        return imageData;
    }

    @Override
    public opencv_core.IplImage scaleImage(opencv_core.IplImage original, int width, int height) {
        opencv_core.IplImage resizedImage = opencv_core.IplImage.create(width, height, original.depth(), original.nChannels());
        cvResize(original, resizedImage);
        return resizedImage;
    }
}
