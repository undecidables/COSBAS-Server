package cosbas.biometric.helper;

/**
 * {@author Renette}
 * Image converter interface for converting images to and from byte[]
 */
public interface ByteImageConverter<ImageType> {
    /**
         * Decodes BinaryType into grayscale ImageType
         * @param data The binary data that should be converted to a grayscale image
         * @return Greyscale image from the binary data
         */
    ImageType createGrayScaleImage(byte[] data);
    /**
         * Encodes ImageType back to BinaryType
         * @param image The image to be encoded
         * @return The encoded result
         */
    byte[] encodeBinary(ImageType image, String format);
    /**
         * Scales {@param image} to {@param width} x {@param height}
         * @param image
         * @param width
         * @param height
         * @return A copy of the image that is scaled
         */
    ImageType scaleImage(ImageType image, int width, int height);
}
