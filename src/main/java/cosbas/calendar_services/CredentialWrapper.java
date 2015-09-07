package cosbas.calendar_services;

import org.springframework.data.annotation.Id;

/**
 * @author Jason Richard Evans
 */

public abstract class CredentialWrapper<T> {
    @Id
    public final String staffID;
    public final T credential;
    public final CalendarType type;

    protected CredentialWrapper(String staffID, T credential, CalendarType type) {
        this.staffID = staffID;
        this.credential = credential;
        this.type = type;
    }
}
