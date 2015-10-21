package cosbas.permissions;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Renette}
 */

public interface PermissionsDAO extends CrudRepository<Permission, String>{
    @Cacheable("Permissions-UserID")
    List<Permission> findByUserID(String userID);
    @Cacheable("Permissions-PermissionID")
    List<Permission> findByPermission(PermissionId permission);
    @Cacheable("Permissions-Both")
    Permission findByUserIDAndPermission(String userID, PermissionId permission);
    List<Permission> deleteByUserIDAndPermission(String userID, PermissionId permission);
}
