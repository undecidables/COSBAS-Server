package cosbas.db.repositories;

import cosbas.permissions.Permission;
import cosbas.permissions.PermissionsDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * {@author Renette Ros}
 */
public interface PermissionsRepository extends PermissionsDAO, MongoRepository<Permission, String> {
}
