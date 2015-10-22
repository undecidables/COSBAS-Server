package cosbas.biometric.validators.facial;

import org.springframework.data.repository.CrudRepository;

/**
 * {@author Renette}
 */
public interface RecognizerDAO extends CrudRepository<RecognizerData, String> {
    RecognizerData findFirstByOrderByUpdatedDesc();
}
