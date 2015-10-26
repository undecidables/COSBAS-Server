package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvMat;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.cvDecodeImage;

/**
 * {@author Reneette}
 */
public interface BiometricsPreprocessor {
    BiometricData processAccess(byte[] data, BiometricTypes type) throws ValidationException;
    BiometricData processRegister(byte[] data, BiometricTypes type);
}
