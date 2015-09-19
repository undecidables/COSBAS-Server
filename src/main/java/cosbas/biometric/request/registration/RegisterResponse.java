package cosbas.biometric.request.registration;

import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;

/**
 * {@author Tienie}
 */
public class RegisterResponse extends AccessResponse {


    private final String email;

    protected RegisterResponse(RegisterRequest request, Boolean result, String message, String userID) {
        super(request, result, message, userID);
        this.email = request.getEmail();
    }

    protected RegisterResponse(AccessRequest request, Boolean result, String message) {
        super(request, result, message);

        this.email = request.getDoorID();

    }

    public static RegisterResponse getSuccessResponse(RegisterRequest re, String message, String user) {
        return new RegisterResponse(re, true, message, user);
    }


    public static RegisterResponse getFailureResponse(AccessRequest re, String message) {
        return new RegisterResponse(re, false, message);
    }
}
