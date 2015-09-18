package cosbas.biometric.request.access;

import cosbas.biometric.data.BiometricData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Renette Ros}
 * An access Request as parsed from the http requests sent to the system.
 */
public class AccessRequest {
    /**
     * Action(eg. exit or entrance) sent from client
     */
    private final DoorActions action;
    /**
     * Door ID from client Request.
     */
    private final String doorID;
    /**
     * Time request object created on server..
     */
    private final LocalDateTime time = LocalDateTime.now();
    /**
     * Array of biometric data from request.
     */
    private final List<BiometricData> data;

    public AccessRequest(String doorID, DoorActions action, List<BiometricData> data) {
        this.doorID = doorID;
        this.data = data;
        this.action = action;
    }

    public String getDoorID() {
        return doorID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<BiometricData> getData() {
        return data;
    }

    public DoorActions getAction() {
        return action;
    }

}
