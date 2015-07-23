package db.repositories;

import InformationPortal.Post;
import InformationPortal.PostDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author Vivian Venter
 * Interface that handles fetching Access Objects from the mongo DB and saving the Access objects in the db.
 * Should not be implemented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AccessDBAdapter interface.
 */
public interface PostRepository  extends MongoRepository<Post, String>, PostDBAdapter {
}
