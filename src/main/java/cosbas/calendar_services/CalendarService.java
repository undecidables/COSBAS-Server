package cosbas.calendar_services;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public abstract class CalendarService {
    final String SUMMARY = "COSBAS BOOKING: ";
    public abstract boolean authorize(String emplid);
    public abstract List<String> getWeeksAppointments(String emplid);
    public abstract String makeAppointment(String emplid, LocalDateTime start, int Duration, String clientName, String clientEmail);
    public abstract boolean removeAppointment(String emplid, String clientEmail);
    public abstract List<String> getTodaysAppointments(String emplid);
}
