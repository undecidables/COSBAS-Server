package cosbas.appointment;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Renette
 * Class describing an appointment that can be directly stored in the database
 */
public class Appointment {

    @Id
    private String id;

    private String eventID; //Needed for deletion of events.
    private String	staffID;
    private List<String> visitorIDs; //Maybe use email address since its unique?

    private List<String> accessKeys;

    private LocalDateTime dateTime;
    private int durationMinutes; //Maybe force multiples of 15/30?

    private String summary;
    private String reason;
    private String status; //OR this could be an enum....


    public Appointment(String staffID, List<String> visitorIDs, LocalDateTime dateTime, int durationMinutes, String reason) {
        this.staffID = staffID;
        this.visitorIDs = visitorIDs;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.reason = reason;
        this.status = "requested";
        this.eventID = null;
        this.summary = null;
    }

    public void setAccessKeys(List<String> accessKeys) {
        this.accessKeys = accessKeys;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public String getStaffID() {
        return staffID;
    }

    public List<String> getVisitorIDs() {
        return visitorIDs;
    }

    public List<String> getAccessKeys() {
        return accessKeys;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
