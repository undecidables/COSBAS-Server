package cosbas.biometric.request.access;

import cosbas.biometric.request.DoorActions;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

/**
 * This class stores the times when specific user was authenticated  by the Biometric System.
 * It exists for reporting and logging purposes.
 */
public class AccessRecord {

    @Id
    private String id;

    private String doorID;

    private LocalDateTime dateTime;
    private DoorActions action;
    private String userID;

    public AccessRecord(String doorID, LocalDateTime dateTime, DoorActions action, String userID) {
        this.doorID = doorID;
        this.dateTime = dateTime;
        this.action = action;
        this.userID = userID;
    }

    public String getDoorID() {
        return doorID;
    }

    public void setDoorID(String doorID) {
        this.doorID = doorID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DoorActions getAction() {
        return action;
    }

    public void setAction(DoorActions action) {
        this.action = action;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}