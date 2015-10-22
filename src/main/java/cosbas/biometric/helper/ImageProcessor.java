package cosbas.biometric.helper;

import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * {@author Renette}
 * Image converter interface for converting images to and from byte[]
 */
public interface ImageProcessor<ImageType> {
    /**
         * Decodes BinaryType into grayscale ImageType
         * @param data The binary data that should be converted to a grayscale image
         * @return Greyscale image from the binary data
         */
    ImageType grayScalefromBytes(byte[] data);
    /**
         * Encodes ImageType back to BinaryType
         * @param image The image to be encoded
         * @return The encoded result
         */
    byte[] toBytes(ImageType image, String format);

    /**
         * Scales {@param image} to {@param width} x {@param height}
         * @param image
         * @param width
         * @param height
         * @return A copy of the image that is scaled
         */
    ImageType scaleImage(ImageType image, int width, int height);

    /**
     * Crops a rectangle out of an image and returns a deep copy
     * @param mat
     * @param rectangle
     * @return
     */
    ImageType crop(ImageType mat, Rectangle rectangle);

    double getCenterX(ImageType t);

    double getCenterY(ImageType t);
}
