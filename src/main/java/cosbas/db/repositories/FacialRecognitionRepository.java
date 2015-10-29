package cosbas.db.repositories;

import cosbas.biometric.validators.facial.DataComponentDAO;
import cosbas.biometric.validators.facial.RecognizerDAO;
import cosbas.biometric.validators.facial.RecognizerData;
import cosbas.biometric.validators.facial.RecognizerDataComponent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Renette}
 * THe Spring Data repository for fetching Facial Recognition training Data
 */
public interface FacialRecognitionRepository extends MongoRepository<RecognizerDataComponent, String>, DataComponentDAO{}
