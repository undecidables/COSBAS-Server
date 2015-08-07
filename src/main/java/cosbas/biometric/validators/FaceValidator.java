package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import org.springframework.stereotype.Component;

/**
 * @author Renette
 * Validates temporary or permanent access code
 */
@Component
public class FaceValidator extends AccessValidator {

    @Override
    public boolean validate(BiometricData request) {
        byte[] img  = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
