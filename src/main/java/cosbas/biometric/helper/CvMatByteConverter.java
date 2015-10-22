package cosbas.biometric.helper;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.stereotype.Component;


import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_core.cvReleaseData;
import static org.bytedeco.javacpp.opencv_highgui.cvDecodeImageM;
import static org.bytedeco.javacpp.opencv_highgui.cvEncodeImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;


/**
 * @author Renette
 * Convertes data between byte[] and openCV mat
 */
@Component
public class CvMatByteConverter implements ByteImageConverter<opencv_core.CvMat> {

    @Override
    public opencv_core.CvMat createGrayScaleImage(byte[] data) {
        return cvDecodeImageM(cvMat(1, data.length, CV_8UC1, new BytePointer(data)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    @Override
    public byte[] encodeBinary(opencv_core.CvMat mat, String format) {

        if (!format.startsWith(".")) format = "." + format;

        opencv_core.CvMat m = cvEncodeImage(format, mat);
        BytePointer bytePointer = m.data_ptr();

        byte[] imageData = new byte[m.size()];
        bytePointer.get(imageData, 0, m.size());
        cvReleaseData(m);

        return imageData;
    }

    @Override
    public opencv_core.CvMat scaleImage(opencv_core.CvMat original, int width, int height) {
        opencv_core.CvMat resizedImage = opencv_core.CvMat.create(height, width, original.depth(), original.nChannels());
        cvResize(original, resizedImage);
        return resizedImage;
    }
}
