package cosbas.appointment;

import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Vivian Venter
 */
public class DeletedAppointment extends Appointment {

    @PersistenceConstructor
    public DeletedAppointment(String staffID, List<String> visitorIDs, LocalDateTime dateTime, int durationMinutes, String reason, String appointmentID) {
        super(staffID, visitorIDs, dateTime, durationMinutes, reason);
        setId(appointmentID);
    }
}
