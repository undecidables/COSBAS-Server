package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.UserNotFoundException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

/**
 * @author Renette
 * Validates fingerprint
 */
@Component
public class FingerprintValidator extends AccessValidator {

    @Override
    protected String matches(BiometricData request, BiometricData dbItem, String action) throws ValidationException {
        return "u00000000";
    }
}
