package cosbas.calendar_services;

import java.util.List;

/**
 * @author Jason Richard Evans
 */
public interface CalendarService {
    public boolean authorize();
    public List<String> getWeeksAppointments();
    public boolean makeAppointment();
    public boolean removeAppointment();
    public List<String> getTodaysAppointments();
}
