package cosbas.biometric.request.access;

import org.springframework.data.repository.CrudRepository;

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
    List<AccessRecord> findByPersonID(String PersonID);

    AccessRecord findById(String id);
	
}
