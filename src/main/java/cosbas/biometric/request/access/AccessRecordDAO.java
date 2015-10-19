package cosbas.biometric.request.access;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface AccessRecordDAO extends CrudRepository<AccessRecord, String> {

    List<AccessRecord> findByDoorID(String DoorID);
    List<AccessRecord> findByAction(String Action);
    List<AccessRecord> findByUserID(String userID);

    AccessRecord findById(String id);

    List<AccessRecord> findByDateTimeBetween(LocalDateTime dateS, LocalDateTime dateE);
    List<AccessRecord> findByUserIDAndDateTimeBetween(String userID, LocalDateTime dateS, LocalDateTime dateE);
	
}
