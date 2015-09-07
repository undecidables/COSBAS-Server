package cosbas.db.repositories;

import cosbas.calendar_services.CalendarDBAdapter;
import cosbas.calendar_services.CredentialWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jason Richard Evans
 */
public interface CalCredentialRepository extends MongoRepository<CredentialWrapper, String>, CalendarDBAdapter {
}
