package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.fingerprint.FingerprintMatching;
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

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) {
        FingerprintMatching matcher = new FingerprintMatching(request.getData());
        List<BiometricData> items = repository.findByType(request.getType());

        for (BiometricData item : items) {
            ValidationResponse response = matcher.matches(item, request.getUserID(), action);
            if (response.approved) return response;
        }
        return ValidationResponse.failedValidation("No Match found");
    }
}
