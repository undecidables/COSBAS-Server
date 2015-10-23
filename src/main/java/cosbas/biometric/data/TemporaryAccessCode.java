package cosbas.biometric.data;

import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;

/**
 * {@author Renette Ros}
 */
public class TemporaryAccessCode extends AccessCode {
    private LocalDateTime validFrom = null;
    private LocalDateTime validTo = null;
    private String appointmentID = null;

    @PersistenceConstructor
    public TemporaryAccessCode(String user, byte[] code, LocalDateTime from, LocalDateTime to, String appointmentID) {
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

    public String getAppointmentID() {
        return appointmentID;
    }

}
