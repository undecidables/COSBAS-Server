package cosbas.biometric.validators;

import cosbas.biometric.request.BiometricData;

/**
 * Created by Renette on 2015-06-26.
 * Validates temporary access code
 */
public class CodeValidator implements AccessValidator {

    @Override
    public boolean validate(BiometricData request) {
        byte[] code = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
