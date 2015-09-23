package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.access.DoorActions;
import org.springframework.stereotype.Component;

import java.util.List;

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


    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) {
        return ValidationResponse.successfulValidation("u00000000");
    }

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) {
        List<BiometricData> items = repository.findByType(request.getType());

        for (BiometricData item : items) {
            ValidationResponse response = matches(request, item, action);
            if (response.approved) return response;

        }
        return ValidationResponse.failedValidation("No Match found");
    }
}
