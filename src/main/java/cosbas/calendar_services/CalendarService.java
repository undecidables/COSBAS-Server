package cosbas.calendar_services;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public interface CalendarService {
    final String SUMMARY = "COSBAS BOOKING: ";
    public Object credential = null;
    public boolean authorize(String emplid);
    public List<String> getWeeksAppointments(String emplid);
    public String makeAppointment(String emplid, LocalDateTime start, int Duration, String clientName, String clientEmail);
    public boolean removeAppointment(String emplid, String clientEmail);
    public List<String> getTodaysAppointments(String emplid);
}
