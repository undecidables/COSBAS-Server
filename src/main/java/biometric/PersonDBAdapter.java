package biometric;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Vivian Venter
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface PersonDBAdapter extends CrudRepository<Person, String> {

    List<Person> findByPersonID (String PersonID);
    Person findById(String id);

}
