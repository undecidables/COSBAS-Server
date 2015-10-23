package cosbas.biometric.data;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.request.DoorActions;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;

/**
 * {@author Renette Ros}
 */
public class TemporaryAccessCode extends AccessCode {
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String appointmentID;

    public String getAppointmentID() {
        return appointmentID;
    }


    @PersistenceConstructor
    protected TemporaryAccessCode(String userID, BiometricTypes type, byte[] data, boolean temporary, DoorActions lastAction, LocalDateTime validFrom, LocalDateTime validTo, String appointmentID) {
        super(userID, type, data, temporary, lastAction);
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.appointmentID = appointmentID;
    }

    public TemporaryAccessCode(String appointmentID, String user, byte[] code, LocalDateTime from, LocalDateTime to) {
        super(user, code);
        this.validFrom = from;
        this.validTo = to;
        this.appointmentID = appointmentID;
        this.temporary = true;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }
}
