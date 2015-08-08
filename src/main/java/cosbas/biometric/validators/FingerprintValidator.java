package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import org.springframework.stereotype.Component;

/**
 * @author Renette
 * Validates fingerprint
 */
@Component
public class FingerprintValidator extends AccessValidator {

    @Override
    protected boolean matches(BiometricData request, BiometricData dbItem) {
        return false;
    }

    @Override
    public boolean validate(BiometricData request) {
        byte[] data = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
