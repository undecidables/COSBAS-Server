package cosbas.biometric.data;

import cosbas.biometric.validators.BiometricTypes;

import java.time.LocalDateTime;

/**
 * @author Renette
 */
public class AccessCode extends BiometricData {

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public AccessCode (String person, byte[] code, LocalDateTime from, LocalDateTime to) {
       super(person, BiometricTypes.CODE, code);
        validFrom = from;
        validTo = to;
    }

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
