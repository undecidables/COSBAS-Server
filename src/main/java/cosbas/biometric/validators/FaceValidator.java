package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import org.opencv.core.Core;
import org.opencv.contrib.Contrib;
import org.opencv.highgui.Highgui;

/**
 * @author Renette
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {

    @Override
    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) throws ValidationException {
        return ValidationResponse.failedValidation("Not impemented yet.");
    }

    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FACE;
    }
}
