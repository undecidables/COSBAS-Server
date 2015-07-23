package biometric.validators;

import biometric.request.BiometricData;

/**
 * Created by Renette on 2015-06-26.
 * Validates temporary access code
 */
public class FaceValidator implements AccessValidator {

    @Override
    public boolean validate(BiometricData request) {
        byte[] img  = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
