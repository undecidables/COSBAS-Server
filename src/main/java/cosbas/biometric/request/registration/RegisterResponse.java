package cosbas.biometric.request.registration;

import java.time.LocalDateTime;

/**
 * {@author Tienie}
 */
public class RegisterResponse {

    /**
     * Time request was sent - for logging purposes.
     */
    private final LocalDateTime requestTime;
    /**
     * Time response object created - for logging purposes.
     */
    private final LocalDateTime responseTime = LocalDateTime.now();
    private final Boolean result;
    private final String message;


    protected RegisterResponse(RegisterRequest request, Boolean result, String message) {
       this.requestTime = request.getTime();
       this.result = result;
       this.message = message;
    }


    public static RegisterResponse getSuccessResponse(RegisterRequest re, String message ){
        return new RegisterResponse(re, true, message);
    }

    public static RegisterResponse getFailureResponse(RegisterRequest re, String message) {
        return new RegisterResponse(re, false, message);
    }
}
