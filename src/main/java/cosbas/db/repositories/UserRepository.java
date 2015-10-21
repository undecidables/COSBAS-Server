package cosbas.db.repositories;

import cosbas.user.User;
import cosbas.user.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Renette Ros}
 */
public interface UserRepository extends UserDAO, MongoRepository<User, String> {
}
