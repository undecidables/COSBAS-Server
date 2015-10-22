package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;

/**
 * @author Renette
 */
public class CvMatStorage implements StorageWrapper<opencv_core.CvMat>
{
    CvMatStorage(opencv_core.CvMat matrix) {
        matrix.data_ptr();
    }
    @Override
    public opencv_core.CvMat getItem() {
        return null;
    }
}
