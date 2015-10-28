package cosbas.appointment;

import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Vivian Venter
 */
public class DeletedAppointment extends Appointment {

    private String appointmentID;

    @PersistenceConstructor
    public DeletedAppointment(String staffID, List<String> visitorIDs, List<String> visitorNames, LocalDateTime dateTime, int durationMinutes, String reason, String appointmentID) {
        super(staffID, visitorIDs, visitorNames, dateTime, durationMinutes, reason);
        this.appointmentID = appointmentID;
        setId(appointmentID);
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

}
