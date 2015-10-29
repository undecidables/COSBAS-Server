package cosbas.biometric.validators;

import com.google.api.client.util.Value;
import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
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
    private BiometricDataDAO fingerprintRepository;

    @Override
    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.FINGER;
    }

    @Value("${fingers.threshold}")
    private int threshold;

    public ValidationResponse identifyUser(BiometricData request, DoorActions action) {
        FingerprintMatching matcher = new FingerprintMatching(request.getData(), request.getUserID(),threshold);

        List<BiometricData> items = fingerprintRepository.findByUserID(request.getUserID());

        for (BiometricData dbItem : items) {
            ValidationResponse response = matcher.matches((FingerprintTemplateData) dbItem, request.getUserID(), action);
            if (response.approved) return response;
        }
        return ValidationResponse.failedValidation("No Match found");
    }
}
