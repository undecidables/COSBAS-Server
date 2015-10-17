package cosbas.db.repositories;

import cosbas.biometric.request.access.AccessRecord;
import cosbas.biometric.request.access.AccessRecordDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching AccessRecord Objects from the mongo DB and saving the AccessRecord objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AccessRecordDAO interface.
 */

interface AccessRepository  extends MongoRepository<AccessRecord, String>, AccessRecordDAO {
}
