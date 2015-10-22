package cosbas.biometric.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    @Value("${faces.imageSize}")
    private int imageSize;

    @Autowired
    protected FaceDetector(ImageProcessor<T> converter) {
        this.converter = converter;
    }

     protected FaceDetector(ImageProcessor<T> converter, int imageSize) {
        this.converter = converter;
         this.imageSize = imageSize;
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
        T result = converter.scaleImage(converter.crop(image, center), imageSize, imageSize);

        return converter.toBytes(result, resultEncoding);
    }
}
