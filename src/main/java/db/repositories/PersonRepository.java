package db.repositories;

import biometric.Person;
import biometric.PersonDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching Person Objects from mongoDB and saving the Person objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the PersonDBAdapter interface.
 */
public interface PersonRepository  extends MongoRepository<Person, String>, PersonDBAdapter {
}
