package cosbas.biometric.request;

import java.time.LocalDateTime;

/**
 * Created by Renette on 2015-06-27.
 * Response to AccessRequest.
 */
public class AccessResponse {
    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getDoorID() {
        return doorID;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    /**
     * Should the data be allowed in.
     * (True = allowed, false = rejected)
     */
    private final Boolean result;

    /**
     * Reason for acceptance/rejection.
     */
    private final String message;

    /**
     * Door ID as sent by client request
     */
    private final String doorID;

    /**
     * If a user is identified their userID, otherwise null;
     */
    private final String userID;

    /**
     * Time request was sent - for logging purposes.
     */
    private final LocalDateTime requestTime;

    /**
     * Time response object created - for logging purposes.
     */
    private final LocalDateTime responseTime = LocalDateTime.now();

    protected AccessResponse(AccessRequest request, Boolean result, String message, String userID) {
        this.result = result;
        this.message = message;
        this.doorID = request.getDoorID();
        this.requestTime = request.getTime();
        this.userID = userID;
    }

    protected AccessResponse(AccessRequest request, Boolean result, String message) {
        this.result = result;
        this.message = message;
        this.doorID = request.getDoorID();
        this.requestTime = request.getTime();
        this.userID = null;
    }

    public static AccessResponse getSuccessResponse(AccessRequest re, String message, String user) {
        return new AccessResponse(re, true, message, user);
    }


    public static AccessResponse getFailureResponse(AccessRequest re, String message) {
        return new AccessResponse(re, false, message);
    }
}
