package cosbas.db.repositories;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.AccessCodeDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching appointment Objects from the mongo DB and saving accesscode objects in the db.
 * Should not be implmented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AccessCodeDBAdapter interface.
 */
public interface TemporaryAccessCodeRepository extends MongoRepository<AccessCode,String>, AccessCodeDBAdapter {
}