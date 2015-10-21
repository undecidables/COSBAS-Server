package cosbas.db.repositories;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching BiometricData Objects from and saving them to the mongoDB.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the BiometricDataDAO interface.
 */
interface BiometricDataRepository extends MongoRepository<BiometricData, String>, BiometricDataDAO {
}
