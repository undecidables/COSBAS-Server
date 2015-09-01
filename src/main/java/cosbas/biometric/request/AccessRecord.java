package cosbas.biometric.request;

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
    private String personID;

    public AccessRecord(String DoorID, LocalDateTime dateTime, DoorActions Action, String PersonID) {
        this.doorID = DoorID;
        this.dateTime = dateTime;
        this.action = Action;
        this.personID = PersonID;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}