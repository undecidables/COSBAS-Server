package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.biometric.validators.facial.FaceRecognition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_legacy.*;


/**
 * {@author Renette Ros}
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {


    private FaceRecognition recognizer;

    @Value("${faces.certainty:0.6}")
    private double certaintyThreshold;

    @Autowired
    public FaceValidator(FaceRecognition recognizer) {
        this.recognizer = recognizer;
    }

    public void setCertaintyThreshold(double certaintyThreshold) {
        this.certaintyThreshold = certaintyThreshold;
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

    @Scheduled(cron = "0 0 0 * * *")
    public void train() {
        if (recognizer.needsTraining()) {
            recognizer.trainFromDB();
        }
    }

    public void forceTrain() {
        recognizer.trainFromDB();
    }

    @Override
    public void registerUser(BiometricData request, String userID) throws BiometricTypeException {
        super.registerUser(request, userID);
        recognizer.setNeedsTraining();
    }

    public void deregisterUser(BiometricData request) throws BiometricTypeException {
        super.deregisterUser(request);
        recognizer.setNeedsTraining();
    }

}
