package cosbas.appointment;

import cosbas.biometric.data.AccessCode;
import org.apache.commons.lang.StringUtils;
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
    private List<String> visitorIDs;
    private List<String> visitorNames;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private String summary;
    private String reason;
    private String status;


    public Appointment(String staffID, List<String> visitorIDs, List<String> visitorNames, LocalDateTime dateTime, int durationMinutes, String reason) {
        this.staffID = staffID;
        this.visitorIDs = visitorIDs;
        this.visitorNames = visitorNames;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.reason = reason;
        this.status = "requested";
        this.eventID = null;
        this.summary = null;
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

    public List<String> getVisitorNames(){
        return visitorNames;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getSummary() {
        return summary;
    }

    public String getListOfVisitors() {
        StringBuilder list = new StringBuilder();
        list.append(StringUtils.join(visitorIDs, ", "));

        return list.toString();
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(String id) {
        this.id = id;
    }
}
