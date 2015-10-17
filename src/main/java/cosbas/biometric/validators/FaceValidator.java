package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.biometric.validators.facial.FaceRecognition;
import org.bytedeco.javacpp.opencv_contrib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_legacy.*;

/**
 * {@author Renette Ros}
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {

    @Autowired
    FaceRecognition recognizer;

    public FaceValidator(FaceRecognition recognizer) {
        this.recognizer = recognizer;
    }

    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FACE;
    }

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) throws ValidationException {
       return recognizer.recognizeFace(request);
    }
}
