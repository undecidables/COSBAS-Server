package db.repositories;

import biometric.person.PersonDBAdapter;
import biometric.person.StaffMember;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching StaffMember Objects from mongoDB and saving the StaffMember objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the PersonDBAdapter interface.
 */
public interface PersonRepository  extends MongoRepository<StaffMember, String>, PersonDBAdapter {
}
