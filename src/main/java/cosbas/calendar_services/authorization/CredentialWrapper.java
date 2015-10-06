package cosbas.calendar_services.authorization;

import com.google.api.client.auth.oauth2.Credential;
import cosbas.calendar_services.CalendarType;
import org.springframework.data.annotation.Id;

import java.io.IOException;

/**
 * @author Jason Richard Evans
 */

public abstract class CredentialWrapper<T> {
    @Id
    public final String staffID;
    public final CalendarType type;

    protected CredentialWrapper(String staffID, CalendarType type) {
        this.staffID = staffID;
        this.type = type;
    }

    public abstract String getAccessToken() throws IOException;

    abstract T getCredential();
}
