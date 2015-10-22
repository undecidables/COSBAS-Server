package cosbas.biometric.request.access;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface AccessRecordDAO extends CrudRepository<AccessRecord, String> {

    @Cacheable("Access")
    List<AccessRecord> findByDoorID(String DoorID);
    @Cacheable("Access")
    List<AccessRecord> findByAction(String Action);
    @Cacheable("Access")
    List<AccessRecord> findByUserID(String userID);
    @Cacheable("Access")
    AccessRecord findById(String id);
	
}
