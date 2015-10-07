package cosbas.db.repositories;

import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.CredentialWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jason Richard Evans
 */
public interface CalCredentialRepository extends MongoRepository<CredentialWrapper, String>, CalendarDBAdapter {
}
