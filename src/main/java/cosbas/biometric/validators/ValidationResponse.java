package cosbas.biometric.validators;

import java.util.Objects;

/**
 * Created by Renette on 2015-08-19.
 */
public class ValidationResponse {
    public final boolean approved;

    /**
     * A user id or message.
     */
    public final String data;

    ValidationResponse(boolean approved, String data) {

        this.data = data;
        this.approved = approved;
    }

    static ValidationResponse successfulValidation(String user) {
        return new ValidationResponse(true, user);
    }

    static ValidationResponse failedValidation(String message) {
        return new ValidationResponse(false, message);
    }

    @Override
    public boolean equals(Object other) {
        try {
            ValidationResponse otherResponse = (ValidationResponse) other;
            return approved == otherResponse.approved && Objects.equals(data, otherResponse.data);
        } catch (Exception e) {
            return false;
        }
    }
}
