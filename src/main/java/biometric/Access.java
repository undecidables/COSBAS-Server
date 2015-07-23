package biomteric;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class Access {
	
	@Id
	private String DoorID;
	
	private LocalDateTime dateTime;
	private String Action;
	private String PersonID;
	
	private Access(String DoorID, LocalDateTime dateTime, String Action, String PersonID) {
		this.DoorID = DoorID;
		this.dateTime = dateTime;
		this.Action = Action;
		this.PersonID = PersonID;
	}
	
}