package cosbas.biometric.data;

import java.time.LocalDateTime;

/**
 * @author Renette
 */
public class TemporaryAccessCode extends AccessCode {
    private LocalDateTime validFrom = null;
    private LocalDateTime validTo = null;
    private String appointmentID = null;

    public TemporaryAccessCode(String person, byte[] code, LocalDateTime from, LocalDateTime to, String appointmentID) {
        super(person, code);
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
