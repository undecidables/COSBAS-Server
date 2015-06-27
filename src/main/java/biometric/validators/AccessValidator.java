package biometric.validators;

import biometric.BiometricData;

/**
 * Created by Renette on 2015-06-26.
 * Validates a specific biometric type.
 */
public interface AccessValidator {
    boolean validate(BiometricData request);
}
