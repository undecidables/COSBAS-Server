package cosbas.calendar_services.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.GoogleCredentialWrapper;
import cosbas.calendar_services.authorization.GoogleVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@author Jason Richard Evans}
 */
@Service
public class GoogleCalendarService extends CalendarService {
    private CalendarDBAdapter credentialRepository;
    //private CalendarFactory calendarServiceFactory;

    @Autowired
    public void setCredentialRepository(CalendarDBAdapter credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    /*@Autowired
    public void setCalendarServiceFactory(CalendarFactory calendarServiceFactory) {
        this.calendarServiceFactory = calendarServiceFactory;
    }*/

    final String SUMMARY = "COSBAS BOOKING: ";
    private final String CALENDAR_ID = "primary";
    private com.google.api.services.calendar.Calendar service;

    @Override
    public List<CosbasEvent> getWeeksAppointments(String emplid) {
        try {
            service = getCalendarService(emplid);
            String pageToken = null;
            List<CosbasEvent> eventList = null;
            do {
                Events events = service.events().list("primary").setPageToken(pageToken)
                        .setMaxResults(25).setTimeMin(toDateTime(LocalDateTime.now()))
                        .setTimeMax(toDateTime((LocalDateTime.now()).plusWeeks(1))).execute();
                List<Event> items = events.getItems();
                if (eventList == null)
                    eventList = new ArrayList<>(items.size());

                for (Event event: items){
                    LocalDateTime start = toLocalDateTime(event.getStart());
                    LocalDateTime end = toLocalDateTime(event.getEnd());
                    CosbasEvent anEvent = new CosbasEvent(event.getId(), event.getAttendees().get(1).toString(), event.getAttendees().get(0).getEmail(), start, end);
                    eventList.add(anEvent);
                }
                pageToken = events.getNextPageToken();
            }
            while (pageToken != null);
            return eventList;
        }
        catch(IOException error){
            error.printStackTrace();
        }
        return new ArrayList<>();
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
            service = getCalendarService(emplid);
            event = service.events().insert(CALENDAR_ID, event).execute();
            return event.getHtmlLink();
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeAppointment(String emplid, String clientEmail) {
        try{
            service = getCalendarService(emplid);
            //service.events().delete(CALENDAR_ID, SUMMARY + " " + clientEmail).execute();
            return true;
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service");
        }
        return false;
    }

    @Override
    public List<String> getTodaysAppointments(String emplid) {
        try {
            service = getCalendarService(emplid);
            String pageToken = null;
            List<String> eventList = null;
            do {
                Events events = service.events().list("primary").setPageToken(pageToken)
                        .setMaxResults(25).setTimeMin(toDateTime(LocalDateTime.now()))
                        .setTimeMax(toDateTime((LocalDateTime.now()).plusHours(24))).execute();
                List<Event> items = events.getItems();
                if (eventList == null)
                    eventList = new ArrayList<>(items.size());

                for (Event event: items){
                    eventList.add(event.getSummary() + " @ "  + event.getStart().toString());
                }
                pageToken = events.getNextPageToken();
            }
            while (pageToken != null);
            return eventList;
        }
        catch(IOException error){
            error.printStackTrace();
        }
        return new ArrayList<>();
    }

    private DateTime toDateTime(LocalDateTime time){
        String sDateTime = time.toString();
        sDateTime += "+02:00";
        return new DateTime(sDateTime);
    }

    private LocalDateTime toLocalDateTime(EventDateTime time){
        String sDateTime = time.getDateTime().toString();
        sDateTime = sDateTime.substring(0, sDateTime.length()-6);
        return LocalDateTime.parse(sDateTime);
    }

    public com.google.api.services.calendar.Calendar getCalendarService(String emplid) throws IOException {
        GoogleCredentialWrapper user = (GoogleCredentialWrapper)credentialRepository.findByStaffID(emplid);
        Credential creds = user.getCredential();
        return new com.google.api.services.calendar.Calendar.Builder(GoogleVariables.HTTP_TRANSPORT, GoogleVariables.JSON_FACTORY, creds)
                .setApplicationName(GoogleVariables.APPLICATION_NAME)
                .build();
    }
}
