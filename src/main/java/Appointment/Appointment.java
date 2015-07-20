package appointment;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Renette
 * Class describing an appointment that can be directly stored in the database
 */
public class Appointment {
    public Appointment(String staffID, List<String> vistorIDs, LocalDateTime dateTime, int durationMinutes, List<String> keys) {
        this.staffID = staffID;
        this.vistorIDs = vistorIDs;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.accessKeys = keys;
    }

    @Id
    private String id;

    private String	staffID;
    private List<String> vistorIDs; //Maybe use email address since its unique?
    private LocalDateTime dateTime;
    private int durationMinutes; //Maybe force multiples of 15/30?
    private List<String> accessKeys;
    private String	status;
}
