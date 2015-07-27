package cosbas.repositories;

import cosbas.biometric.request.AccessDBAdapter;
import cosbas.biometric.request.AccessRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching AccessRecord Objects from the mongo DB and saving the AccessRecord objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AccessDBAdapter interface.
 */

public interface AccessRepository  extends MongoRepository<AccessRecord, String>, AccessDBAdapter {
}
