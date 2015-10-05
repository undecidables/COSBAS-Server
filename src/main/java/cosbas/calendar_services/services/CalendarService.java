package cosbas.calendar_services.services;

import cosbas.appointment.Appointment;
import cosbas.calendar_services.authorization.CalendarDBAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public abstract class CalendarService {

    public void setRepository(CalendarDBAdapter repository) {
        this.repository = repository;
    }

    @Autowired

    protected CalendarDBAdapter repository;

    public abstract List<Appointment> getWeeksAppointments(String emplid);
    public abstract String makeAppointment(String emplid, LocalDateTime start, int Duration, String clientName, String clientEmail);
    public abstract boolean removeAppointment(String emplid, String id);
    public abstract List<Appointment> getTodaysAppointments(String emplid);

}
