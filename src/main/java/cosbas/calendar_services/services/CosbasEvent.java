package cosbas.calendar_services.services;

import java.time.LocalDateTime;

/**
 * {@author Jason Richard Evans}
 * A class that encapsulates information pertaining to a single event in the COSBAS eco-system.
 */
public class CosbasEvent {
    String eventID;
    String client;
    String clientEmail;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Boolean approved;

    public String getEventID() {
        return eventID;
    }

    public String getClient() {
        return client;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public CosbasEvent(String id, String client, String clientEmail, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventID = id;
        this.client = client;
        this.clientEmail = clientEmail;
        this.startTime = startTime;
        this.endTime = endTime;
        this.approved = false;
    }
}
