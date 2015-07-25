package org.undecidables.biometric.validators;

import org.undecidables.biometric.request.BiometricData;

/**
 * Created by Renette on 2015-06-26.
 * Validates temporary access code
 */
public class FingerprintValidator implements AccessValidator {

    @Override
    public boolean validate(BiometricData request) {
        byte[] data = request.getData();
        //Lookup in db
        //Validate
        return true;
    }
}
