package InformationPortal;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Vivian Venter
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface PostDBAdapter extends CrudRepository<Post, String> {

    List<Post> findByPostID (String PostID);
    List<Post> findByAuthorID (String AuthorID);

   // Post findById(String id);

}
