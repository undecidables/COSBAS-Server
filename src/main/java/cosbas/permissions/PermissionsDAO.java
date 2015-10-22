package cosbas.permissions;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * {@author Renette}
 */
public interface PermissionsDAO extends CrudRepository<Permission, String> {
    List<Permission> findByUserID(String userID);

    List<Permission> findByPermission(PermissionId permission);

    Permission findByUserIDAndPermission(String userID, PermissionId permission);

    List<Permission> deleteByUserIDAndPermission(String userID, PermissionId permission);
}
