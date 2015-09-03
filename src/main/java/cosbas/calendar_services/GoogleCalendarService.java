package cosbas.calendar_services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public class GoogleCalendarService implements CalendarService {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
    private final String CALENDAR_ID = "primary";

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean authorize(String emplid) {
        GoogleAuthorization auth = new GoogleAuthorization();
        return false;
    }

    @Override
    public List<String> getWeeksAppointments(String emplid) {
        return null;
    }

    @Override
    public String makeAppointment(String emplid, LocalDateTime start, int Duration, String clientName, String clientEmail) {
        return null;
    }

    @Override
    public boolean removeAppointment(String emplid, String clientEmail) {
        return false;
    }

    @Override
    public List<String> getTodaysAppointments(String emplid) {
        return null;
    }
}
