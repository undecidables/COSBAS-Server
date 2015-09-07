package cosbas.calendar_services;

import com.google.api.client.auth.oauth2.Credential;

/**
 * @author Jason Richard Evans
 */
public class GCredWrapper extends CredentialWrapper<Credential> {
    protected GCredWrapper(String staffID, Credential credential, CalendarType type) {
        super(staffID, credential, type);
    }
}
