package cosbas.db.repositories;

import cosbas.biometric.validators.fingerprint.FingerprintDAO;
import cosbas.biometric.validators.fingerprint.FingerprintTemplateData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Vivian Venter}
 * THe Spring Data repository for fetching fingerprint template data
 */
public interface FingerprintRecognitionRepository extends FingerprintDAO, MongoRepository<FingerprintTemplateData, String>{
}
