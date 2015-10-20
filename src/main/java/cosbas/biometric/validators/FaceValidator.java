package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.biometric.validators.facial.FaceRecognition;
import org.bytedeco.javacpp.opencv_contrib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

    
    FaceRecognition recognizer;

    //@Value ("$(faces.certaintyThreshold:70.0}")
    double certaintyThreshold = 70.0;

    @Autowired
    public FaceValidator(FaceRecognition recognizer) {
        this.recognizer = recognizer;
    }

    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FACE;
    }

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) throws ValidationException {
       ValidationResponse tmp = recognizer.recognizeFace(request);
        if (tmp.certainty > certaintyThreshold && tmp.approved)
            return tmp;
        else
            return ValidationResponse.failedValidation("Recognition too uncertain.");
    }

    //TODO Fix schedule!
    //@Scheduled
    public void train() {
        if (recognizer.getData().needsTraining()) {
            recognizer.trainFromDB();
        }
    }
}
