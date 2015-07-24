package biometric.request;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class AccessRecord {
	
	@Id
	private String id;

	private String DoorID;
	
	private LocalDateTime dateTime;
	private String Action;
	private String PersonID;
	
	public AccessRecord(String DoorID, LocalDateTime dateTime, String Action, String PersonID) {
		this.DoorID = DoorID;
		this.dateTime = dateTime;
		this.Action = Action;
		this.PersonID = PersonID;
	}


	/**
	 * Getter and Setter Functions
	 */
	public String getDoorID() {
		return DoorID;
	}

	public void setDoorID(String doorID) {
		DoorID = doorID;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getPersonID() {
		return PersonID;
	}

	public void setPersonID(String personID) {
		PersonID = personID;
	}
}