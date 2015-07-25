package db.repositories;

import InformationPortal.Post;
import InformationPortal.PostDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Vivian Venter
 * Interface that handles fetching AccessRecord Objects from the mongo DB and saving the AccessRecord objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AccessDBAdapter interface.
 */
@Repository
public interface PostRepository  extends MongoRepository<Post, String>, PostDBAdapter {
}
