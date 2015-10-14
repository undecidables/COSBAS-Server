package cosbas.biometric.validators;

import cosbas.biometric.request.access.AccessRequest;

/**
 * {@author Renette Ros}
 * ValidationResponses are created by the {@link AccessValidator}s for each data object in an {@link AccessRequest}
 */
public class ValidationResponse {
    public final boolean approved;

    /**
     * A user id or message.
     */
    public final String data;
    /* A number between 0 and 1 */
    public final double certainty;

    ValidationResponse(boolean approved, String data, int certainty) {
        this.data = data;
        this.approved = approved;
        this.certainty = certainty;
    }

    ValidationResponse(boolean approved, String data, int certaintyPercent) {
     this(approved, data, 1.0)
    }

    public static ValidationResponse successfulValidation(String user) {
        return new ValidationResponse(true, user);
    }

    public static ValidationResponse failedValidation(String message) {
        return new ValidationResponse(false, message);
    }
}
