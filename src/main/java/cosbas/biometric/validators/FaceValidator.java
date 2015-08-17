package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.UserNotFoundException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

/**
 * @author Renette
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {

    @Override
    protected String matches(BiometricData request, BiometricData dbItem, String action) throws ValidationException {
        return "u00000000";
    }
}
