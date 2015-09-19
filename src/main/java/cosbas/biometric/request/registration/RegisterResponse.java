package cosbas.biometric.request.registration;

import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;

/**
 * {@author Tienie}
 */
public class RegisterResponse extends AccessResponse {


    protected RegisterResponse(RegisterRequest request, Boolean result, String message, String userID) {
        super(request, result, message, userID);
    }

    protected RegisterResponse(AccessRequest request, Boolean result, String message) {
        super(request, result, message);
    }

    public static RegisterResponse getSuccessResponse(RegisterRequest re, String message, String user) {
        return new RegisterResponse(re, true, message, user);
    }

    public static RegisterResponse getFailureResponse(AccessRequest re, String message) {
        return new RegisterResponse(re, false, message);
    }
}
