package cosbas.calendar_services.services;

import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.Authorizer;
import cosbas.calendar_services.authorization.GoogleAuthorization;
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

    public abstract List<String> getWeeksAppointments(String emplid);
    public abstract String makeAppointment(String emplid, LocalDateTime start, int Duration, String clientName, String clientEmail);
    public abstract boolean removeAppointment(String emplid, String clientEmail);
    public abstract List<String> getTodaysAppointments(String emplid);

}
