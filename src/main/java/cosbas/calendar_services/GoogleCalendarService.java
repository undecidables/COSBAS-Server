package cosbas.calendar_services;

import java.util.List;

/**
 * @author Jason Richard Evans
 */
public class GoogleCalendarService implements CalendarService {
    @Override
    public boolean authorize() {
        return false;
    }

    @Override
    public List<String> getWeeksAppointments() {
        return null;
    }

    @Override
    public boolean makeAppointment() {
        return false;
    }

    @Override
    public boolean removeAppointment() {
        return false;
    }

    @Override
    public List<String> getTodaysAppointments() {
        return null;
    }
}
