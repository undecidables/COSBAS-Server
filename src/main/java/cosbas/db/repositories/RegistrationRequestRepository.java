package cosbas.db.repositories;

import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterRequestDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Renette Ros}
 */
public interface RegistrationRequestRepository extends MongoRepository<RegisterRequest, String>, RegisterRequestDAO {
}
