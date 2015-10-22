package cosbas.db.repositories;

import cosbas.biometric.validators.facial.RecognizerDAO;
import cosbas.biometric.validators.facial.RecognizerData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Renette}
 * THe Spring Data repository for fetching Facial Recognition training Data
 */
public interface FacialRecognitionRepository extends RecognizerDAO, MongoRepository<RecognizerData, String>{}
