package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.fingerprint.FingerprintDAO;
import cosbas.biometric.validators.fingerprint.FingerprintMatching;
import cosbas.biometric.validators.fingerprint.FingerprintTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@author Renette Ros}
 * Validates fingerprint
 */
@Component
public class FingerprintValidator extends AccessValidator {

    @Autowired
    private FingerprintDAO fingerprintRepository;

    @Override
    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FINGER;
    }

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) {
        FingerprintMatching matcher = new FingerprintMatching(request.getData());

        List<FingerprintTemplateData> items = fingerprintRepository.findByStaffID(request.getUserID());

        for (FingerprintTemplateData dbItem : items) {
            ValidationResponse response = matcher.matches(dbItem,request.getUserID(), action);
            if (response.approved) return response;
        }
        return ValidationResponse.failedValidation("No Match found");
    }
}
