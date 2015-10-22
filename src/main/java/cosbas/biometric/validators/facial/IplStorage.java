package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.PersistenceConstructor;

import java.nio.ByteBuffer;

/**
 * {@author Renette}
 */
public class IplStorage implements StorageWrapper<opencv_core.IplImage> {


    private ByteBuffer buffer;
    private int width;
    private int height;
    private int depth;
    private int channels;

    @PersistenceConstructor
    public IplStorage(ByteBuffer buffer, int width, int height, int depth, int channels) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.channels = channels;
    }

    public IplStorage(opencv_core.IplImage image) {
        buffer = image.imageData().asBuffer();
        width = image.width();
        height = image.height();
        depth = image.depth();
        channels = image.nChannels();
    }


    @Override
    public opencv_core.IplImage getItem() {
        opencv_core.IplImage img = opencv_core.IplImage.create(width, height, depth, channels);
        img.imageData(new BytePointer(buffer));
        return img;
    }
}
