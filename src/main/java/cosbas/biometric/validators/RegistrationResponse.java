package cosbas.biometric.validators;

import java.util.Objects;

/**
 * Created by Tienie on 13/09/2015.
 */
public class RegistrationResponse {
    public final boolean approved;

    /**
     * A user id or message.
     */
    public final String data;

    RegistrationResponse(boolean approved, String data) {

        this.data = data;
        this.approved = approved;
    }

    public static RegistrationResponse successfulRegistration(boolean approved, String user) {
        return new RegistrationResponse(true, user);
    }

    public static RegistrationResponse failedRegistration(String message) {
        return new RegistrationResponse(false, message);
    }

    @Override
    public boolean equals(Object other) {
        try {
            RegistrationResponse otherResponse = (RegistrationResponse) other;
            return approved == otherResponse.approved && Objects.equals(data, otherResponse.data);
        } catch (Exception e) {
            return false;
        }
    }
}
