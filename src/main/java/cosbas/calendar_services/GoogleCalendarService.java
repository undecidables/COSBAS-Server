package cosbas.calendar_services;

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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
    public String makeAppointment(String emplid, LocalDateTime startTime, int Duration, String clientName, String clientEmail) {
        //TO DO get the service object
        Event event = new Event()
                .setSummary(SUMMARY)
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

        //event = service.events().insert(calendarId, event).execute();
        //return event.getHtmlLink();
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

    private DateTime toDateTime(LocalDateTime time){
        String sDateTime = time.toString();
        sDateTime += "+02:00";
        DateTime newTime = new DateTime(sDateTime);
        return newTime;
    }
}
