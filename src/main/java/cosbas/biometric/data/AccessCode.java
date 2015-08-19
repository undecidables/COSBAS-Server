package cosbas.biometric.data;

import cosbas.biometric.validators.BiometricTypes;
import cosbas.biometric.validators.DoorActions;
import org.springframework.data.annotation.PersistenceConstructor;

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

    @PersistenceConstructor
    public AccessCode (String personID, byte[] data, LocalDateTime validFrom, LocalDateTime validTo) {
       super(personID, BiometricTypes.CODE, data);
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public AccessCode (String person, byte[] code) {
        super(person, BiometricTypes.CODE, code);
    }

    public AccessCode(byte[] code) {
        super(BiometricTypes.CODE, code);
    }

    public void use(DoorActions action) {
        lastAction = action;
    }

    public DoorActions getLastAction() {
        return lastAction;
    }

    private LocalDateTime validFrom = null;
    private LocalDateTime validTo = null;
    private DoorActions lastAction = null;
}
