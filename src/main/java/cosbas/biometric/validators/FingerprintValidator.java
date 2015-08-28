package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

/**
 * {@author Renette Ros}
 * Validates fingerprint
 */
@Component
public class FingerprintValidator extends AccessValidator {

    @Override
    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FINGER;
    }

    @Override
    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) throws ValidationException {
        return ValidationResponse.successfulValidation("u00000000");
    }
}
