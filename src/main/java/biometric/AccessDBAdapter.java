package biometric;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Vivian Venter
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface AccessDBAdapter extends CrudRepository<Access, String> {

    List<Access> findByDoorID(String DoorID);
    Access findById(String id);
	
}
