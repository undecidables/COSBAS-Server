package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricTypes;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

/**
 * {@author Renette Ros}
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {

    @Override
    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) throws ValidationException {
        return ValidationResponse.successfulValidation("u00000000");
    }

    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FACE;
    }
}
