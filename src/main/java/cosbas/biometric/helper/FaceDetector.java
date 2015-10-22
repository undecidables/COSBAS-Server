package cosbas.biometric.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

/**
 * {@author Renette}
 * Abstract Face detector
 */
@Component
public abstract class FaceDetector<T> {

    @Autowired
    final ImageProcessor<T> converter;

    @Autowired
    protected FaceDetector(ImageProcessor<T> converter) {
        this.converter = converter;
    }

    private void test() {
        System.out.print("");
    }


    /**
     * Detects faces, returns list of bounding Rectangles
     * @param image
     * @return
     */
    public abstract List<Rectangle> detectFaces(T image);

    /**
     * Determine most center rectangle based om center of image
     * @param rects
     * @param imageCenterX
     * @param imageCenterY
     * @return The rectangle closes to the defined center
     */
    public abstract Rectangle mostCenterRect(List<Rectangle> rects, double imageCenterX, double imageCenterY);

    public byte[] getCenterFace(byte[] data, String resultEncoding) {
        T image = converter.grayScalefromBytes(data);
        List<Rectangle> rectangles = detectFaces(image);
        Rectangle center = mostCenterRect(rectangles, converter.getCenterX(image), converter.getCenterY(image));
        T result = converter.crop(image, center);
        return converter.toBytes(result, resultEncoding);
    }
}
