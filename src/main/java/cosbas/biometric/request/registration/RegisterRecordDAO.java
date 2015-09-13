package cosbas.biometric.request.registration;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by Tienie on 13/09/2015.
 */
public interface RegisterRecordDAO extends CrudRepository<RegisterRecord, String> {
    List<RegisterRecord> findByDoorID(String DoorID);
    List<RegisterRecord> findByAction(String Action);
    List<RegisterRecord> findByPersonID(String PersonID);

    RegisterRecord findById(String id);
}
