package cosbas.biometric.helper;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.stereotype.Component;

import java.awt.*;

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
public class IplProcessor implements ImageProcessor<opencv_core.IplImage> {
    @Override
    public opencv_core.IplImage grayScalefromBytes(byte[] data) {
        return cvDecodeImage(cvMat(1, data.length, CV_8UC1, new BytePointer(data)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    @Override
    public byte[] toBytes(opencv_core.IplImage image, String format) {
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

    @Override
    public opencv_core.IplImage crop(opencv_core.IplImage iplImage, Rectangle rectangle) {
        opencv_core.Mat tmp = new opencv_core.Mat(iplImage);
        return (new opencv_core.Mat(tmp, new opencv_core.Rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)).clone().asIplImage());
    }

    @Override
    public double getCenterX(opencv_core.IplImage t) {
        return t.width()/2.0;
    }

    @Override
    public double getCenterY(opencv_core.IplImage t) {
        return t.height()/2.0;
    }

    @Override
    public int getWidth(opencv_core.IplImage image) {
        return image.width();
    }

    @Override
    public int getHeight(opencv_core.IplImage image) {
        return image.height();
    }


}
