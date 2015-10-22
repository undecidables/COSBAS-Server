package cosbas.biometric.helper;

import org.bytedeco.javacpp.opencv_objdetect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * {@author Renette}
 */
@Component
public class OpenCVFaceDetector {

    private opencv_objdetect.CascadeClassifier faceDetector;
    private final String classifierFilename = "faces/haarcascade_frontalface_alt.xml";

    @PostConstruct
    public void constructClassifier() throws IOException {
        try {
            faceDetector = new opencv_objdetect.CascadeClassifier(classifierFilename);
        }
        catch (Exception e) {
            throw new IOException("Cannot load classifier file: " + e.getMessage());
        }
    }
}
