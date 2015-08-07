package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import org.springframework.stereotype.Component;

/**
 * @author Renette
 * Validates temporary access code
 */
@Component
public class CodeValidator extends AccessValidator {

    @Override
    public boolean validate(BiometricData request) {
        byte[] code = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
