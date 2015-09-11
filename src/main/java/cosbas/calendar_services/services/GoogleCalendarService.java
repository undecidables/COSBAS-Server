package cosbas.calendar_services.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * {@author Jason Richard Evans}
 */
@Service
public class GoogleCalendarService extends CalendarService {
    public static Object credential = null;
    private static final String APPLICATION_NAME = "COSBAS Calendar Integration Service";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
    final String SUMMARY = "COSBAS BOOKING: ";
    private final String CALENDAR_ID = "primary";
    private com.google.api.services.calendar.Calendar service;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public List<String> getWeeksAppointments(String emplid) {
        return null;
    }

    @Override
    public String makeAppointment(String emplid, LocalDateTime startTime, int Duration, String clientName, String clientEmail) {

        Event event = new Event()
                .setSummary(SUMMARY + " " + clientEmail)
                .setDescription(clientName + " has an appointment with " + emplid + " at " + startTime.toString());

        DateTime startDateTime = toDateTime(startTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Africa/Johannesburg");
        event.setStart(start);

        DateTime endDateTime = toDateTime(startTime.plusMinutes(Duration));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Africa/Johannesburg");
        event.setEnd(end);

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail(clientEmail),
                new EventAttendee().setEmail(emplid + "@cs.up.ac.za"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverride = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(30),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverride));
        event.setReminders(reminders);

        try{
            service = getCalendarService();
            event = service.events().insert(CALENDAR_ID, event).execute();
            return event.getHtmlLink();
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service");
        }
        return null;
    }

    @Override
    public boolean removeAppointment(String emplid, String clientEmail) {
        try{
            service = getCalendarService();
            service.events().delete(CALENDAR_ID, SUMMARY + " " + clientEmail).execute();
            return true;
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service");
        }
        return false;
    }

    @Override
    public List<String> getTodaysAppointments(String emplid) {
        return null;
    }

    private DateTime toDateTime(LocalDateTime time){
        String sDateTime = time.toString();
        sDateTime += "+02:00";
        DateTime newTime = new DateTime(sDateTime);
        return newTime;
    }

    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, (Credential)credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
