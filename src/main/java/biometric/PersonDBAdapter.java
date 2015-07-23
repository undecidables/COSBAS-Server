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

    Person findByPersonID (String PersonID);

    List<Person> findByName (String Name);
    List<Person> findBySurname (String Surname);
    List<Person> findByEmployID (String EmployID);

  //  Person findById(String id);

}
