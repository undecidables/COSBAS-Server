package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * @author Renette
 */
public class CvMatStorage {

    private final int cols;
    private final int rows;
    private final int type;
    private double[] data;

    @PersistenceConstructor
    public CvMatStorage(int cols, int rows, int type, double[] data) {
        this.cols = cols;
        this.rows = rows;
        this.type = type;
        this.data = data;
    }

    CvMatStorage(opencv_core.IplImage image) {
        this(image.asCvMat());
    }

    CvMatStorage(opencv_core.CvMat image) {
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
