package cosbas.user;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Renette
 */
public interface UserDAO extends CrudRepository<User, String> {
    User findByUserID(String userID);
}
