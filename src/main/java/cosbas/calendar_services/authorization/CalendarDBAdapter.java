package cosbas.calendar_services.authorization;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Jason Richard Evans
 */
public interface CalendarDBAdapter extends CrudRepository<CredentialWrapper, String> {
    CredentialWrapper findByStaffID(String staffID);
}
