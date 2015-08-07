package cosbas.biometric.request;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Vivian Venter
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface AccessRecordDAO extends CrudRepository<AccessRecord, String> {

    List<AccessRecord> findByDoorID(String DoorID);
    List<AccessRecord> findByAction(String Action);
    List<AccessRecord> findByPersonID(String PersonID);

    AccessRecord findById(String id);
	
}
