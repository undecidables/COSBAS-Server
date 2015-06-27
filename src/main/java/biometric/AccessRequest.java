package biometric;

import java.time.LocalDateTime;

/**
 * Created by Renette on 2015-06-27.
 * Access Request.
 * Contains: DoorID as sent by client, current time of the server.
 */
public class AccessRequest {
    public AccessRequest(String doorID, BiometricData[] data) {
        this.doorID = doorID;
        this.data = data;
    }

    public String getDoorID() {
        return doorID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public BiometricData[] getData() {
        return data;
    }

    /**
     * ID from client Request.
     */
    private final String doorID;

    /**
     * Time request object created on server..
     */
    private final LocalDateTime time = LocalDateTime.now();

    /**
     * Array of biometric data from request.
     */
    private final BiometricData[] data;

}
