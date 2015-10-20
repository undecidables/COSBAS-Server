package cosbas.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@author Renette Ros}
 */
@Service
public class PermissionManager {

    private PermissionsDAO permissionsRepository;

    @Autowired
    public PermissionManager(PermissionsDAO permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    public List<Permission> usersForPermission(PermissionId permissions) {
        return permissionsRepository.findByPermission(permissions);
    }

    public Iterable<Permission> permissionsForUser(String userID) {
        return permissionsRepository.findByUserID(userID);
    }

    public boolean hasPermission(String user, PermissionId permissionId) {
        return (permissionsRepository.findByUserIDAndPermission(user, permissionId)) != null;
    }

    public void addPermission(String user, PermissionId permissionId) {
        permissionsRepository.save(new Permission(user, permissionId));
    }

    public void removePermission(String user, PermissionId permissionId) {
        permissionsRepository.deleteByUserIDAndPermission(user, permissionId);
    }

    public PermissionId[] allPermissions() {
        return PermissionId.values();
    }

}
