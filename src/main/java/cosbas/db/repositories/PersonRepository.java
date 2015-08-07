package cosbas.db.repositories;

import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.data.StaffMember;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching StaffMember Objects from mongoDB and saving the StaffMember objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the BiometricDataDAO interface.
 */
public interface PersonRepository  extends MongoRepository<StaffMember, String>, BiometricDataDAO {
}
