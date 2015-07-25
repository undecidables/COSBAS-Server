package org.undecidables.biometric.request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Renette on 2015-06-27.
 * AccessRecord Request.
 * Contains: DoorID as sent by client, current time of the server.
 */
public class AccessRequest {
    public AccessRequest(String doorID, String action, List<BiometricData> data) {
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

    public String getAction()
    {
        return action;
    }

    /**
     * Action(eg. exit or entrance) sent from client
     */
    private final String action;

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
    private final List<BiometricData> data;

}
