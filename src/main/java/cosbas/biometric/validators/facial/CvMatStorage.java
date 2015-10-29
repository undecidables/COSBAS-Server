package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Renette
 */
@Component
public class CvMatStorage extends RecognizerDataComponent {

    private final int cols;
    private final int rows;
    private final int type;
    private final String linkedID;
    private double[] data;

    @PersistenceConstructor
    public CvMatStorage(String id, String field, int cols, int rows, int type, double[] data, LocalDateTime saved, String linkedID) {
        super(id, field, saved);
        this.cols = cols;
        this.rows = rows;
        this.type = type;
        this.data = data;
        this.linkedID = linkedID;
    }

    CvMatStorage(opencv_core.IplImage image, String field, LocalDateTime saved, String linkedID) {
        this(image.asCvMat(), field, saved, linkedID);
    }

    CvMatStorage(opencv_core.CvMat image, String field, LocalDateTime saved, String linkedID) {
        super(field, saved);
        this.linkedID = linkedID;
        cols = image.cols();
        rows = image.rows();
        type = image.type();
        data = image.get();
}
    public opencv_core.CvMat getCVMat() {
        opencv_core.CvMat mat = opencv_core.CvMat.create(rows, cols, type);
        mat.height(rows);
        mat.width(cols);
        mat.data_db(new DoublePointer(data));
        return mat;
    }

    public opencv_core.IplImage getIPL() {
        return getCVMat().asIplImage();
    }

}
