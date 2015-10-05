package cosbas.calendar_services.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
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
    private AppointmentDBAdapter appointmentRepository;
    //private CalendarFactory calendarServiceFactory;

    @Autowired
    public void setCredentialRepository(CalendarDBAdapter credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Autowired
    public void setAppointmentRepository(AppointmentDBAdapter appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    /*@Autowired
    public void setCalendarServiceFactory(CalendarFactory calendarServiceFactory) {
        this.calendarServiceFactory = calendarServiceFactory;
    }*/

    final String SUMMARY = "COSBAS BOOKING: ";
    private final String CALENDAR_ID = "primary";
    private com.google.api.services.calendar.Calendar service;

    /**
     * Functionality to get the current week's appointments directly from
     * the connected google account.
     * @param emplid The employee id of the Employee we are getting the appointments for.
     * @return a List of appointment objects containing the information of events.
     */
    @Override
    public List<Appointment> getWeeksAppointments(String emplid) {
        try {
            service = getCalendarService(emplid);
            String pageToken = null;
            List<Appointment> eventList = null;
            do {
                Events events = service.events().list("primary").setPageToken(pageToken)
                        .setMaxResults(25).setTimeMin(toDateTime(LocalDateTime.now()))
                        .setTimeMax(toDateTime((LocalDateTime.now()).plusWeeks(1))).execute();
                List<Event> items = events.getItems();
                if (eventList == null)
                    eventList = new ArrayList<>(items.size());

                for (Event event: items){
                    Appointment anEvent = toAppointmentObj(event, emplid);
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

    /**
     * Functionality to create a new event in the connected Google calendar.
     * @param emplid The employee id of the employee we are creating the event for.
     * @param startTime A LocalDateTime object of the exact starting time for the event.
     * @param Duration An integer value stating the duration of the event.
     * @param clientName The name of the client creating/requesting the event.
     * @param clientEmail The email address of the client creating the event for direct communication of event changes.
     * @return A string of the html link to the event in their calendar.
     */
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

            List<String> attendants = new ArrayList<>();
            attendants.add(clientEmail);
            attendants.add(emplid + "@cs.up.ac.za");
            Appointment newEvent = new Appointment(emplid, attendants, startTime, Duration, event.getDescription());
            newEvent.setEventID(event.getId());
            newEvent.setSummary(event.getSummary());
            appointmentRepository.save(newEvent);
            return event.getHtmlLink();
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Functionality to remove an event (when being denied or simply cancelled).
     * @param emplid The employee id of the employee with whom the event was scheduled.
     * @param id The id of the event (as saved in mongodb and given to client as event identifier).
     * @return Returns true when the event was successfully removed from both mongodb and the connected calendar and
     * false otherwise.
     */
    @Override
    public boolean removeAppointment(String emplid, String id) {
        try{
            service = getCalendarService(emplid);
            Appointment event = appointmentRepository.findById(id);
            appointmentRepository.delete(event);

            service.events().delete("primary", event.getEventID()).setSendNotifications(true).execute();
            return true;
        }
        catch(IOException e){
            System.out.println("COSBAS Calendar: In GoogleCalendarService could not initialize the service");
        }
        return false;
    }

    /**
     * Functionality to retrieve the current day's appointments (pending and approved).
     * @param emplid The employee id of the employee for whom we are retrieving events for.
     * @return A list of appointment objects that contain the event information.
     */
    @Override
    public List<Appointment> getTodaysAppointments(String emplid) {
        try {
            service = getCalendarService(emplid);
            String pageToken = null;
            List<Appointment> eventList = null;
            do {
                Events events = service.events().list("primary").setPageToken(pageToken)
                        .setMaxResults(25).setTimeMin(toDateTime(LocalDateTime.now()))
                        .setTimeMax(toDateTime((LocalDateTime.now()).plusHours(24))).execute();
                List<Event> items = events.getItems();
                if (eventList == null)
                    eventList = new ArrayList<>(items.size());

                for (Event event: items){
                    Appointment anEvent = toAppointmentObj(event, emplid);
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

    /**
     * A simple helper function to convert date and time information into the appropriate format that the third-party
     * api service expects.
     * @param time The date and time information that needs to be converted.
     * @return Converted date and time information in the correct format.
     */
    private DateTime toDateTime(LocalDateTime time){
        String sDateTime = time.toString();
        sDateTime += "+02:00";
        return new DateTime(sDateTime);
    }

    /**
     * A simple helper function to convert date and time information from third-party api service to our local
     * server date and time format.
     * @param time The third-party date and time information to convert.
     * @return A converted LocalDateTime (Java) object.
     */
    private LocalDateTime toLocalDateTime(EventDateTime time){
        String sDateTime = time.getDateTime().toString();
        sDateTime = sDateTime.substring(0, sDateTime.length()-6);
        return LocalDateTime.parse(sDateTime);
    }

    /**
     * A function that retrives the authenticated service object on which third-party api services can be called.
     * @param emplid The employee id for which we want to preform a calendar connected service for.
     * @return An authenticated third-party calendar service.
     * @throws IOException Some kind of exception.
     */
    public com.google.api.services.calendar.Calendar getCalendarService(String emplid) throws IOException {
        GoogleCredentialWrapper user = (GoogleCredentialWrapper)credentialRepository.findByStaffID(emplid);
        Credential creds = user.getCredential();
        return new com.google.api.services.calendar.Calendar.Builder(GoogleVariables.HTTP_TRANSPORT, GoogleVariables.JSON_FACTORY, creds)
                .setApplicationName(GoogleVariables.APPLICATION_NAME)
                .build();
    }

    /**
     * A simple helper function that converts third-party calendar Event objects to locally understood Appointment
     * objects.
     * @param event The event to be converted.
     * @param emplid The employee id of the employee we are performing the action for.
     * @return An Appointment object with event information.
     */
    private Appointment toAppointmentObj(Event event, String emplid){
        LocalDateTime start = toLocalDateTime(event.getStart());
        LocalDateTime end = toLocalDateTime(event.getEnd());

        //Calculating duration
        int duration = (int)java.time.Duration.between(start, end).toMinutes();

        //Some conversion needed
        List<String> attending = new ArrayList<String>();
        for (EventAttendee attendee: event.getAttendees()){
            attending.add(attendee.getEmail());
        }

        Appointment anEvent = new Appointment(emplid, attending, start, duration, event.getDescription());
        anEvent.setEventID(event.getId());
        anEvent.setSummary(event.getSummary());
        return anEvent;
    }
}