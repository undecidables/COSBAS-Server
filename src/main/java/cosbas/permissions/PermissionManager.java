package cosbas.permissions;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * {@author Renette Ros}
 */
@Service
public class PermissionManager {
    public Iterable<Permission> usersForPermission(PermissionId permissions) {
        return new ArrayList<>();
    }

    public Iterable<Permission> permissionsForUser(String userID) {
        return new ArrayList<>();
    }

    public boolean hasPermission(String user, PermissionId permissionId) {
        return true;
    }

    public void addPermission(String user, PermissionId permissionId) {}

    public void removePermission(String user, PermissionId permissionId) {}

    public PermissionId[] allPermissions() {
        return PermissionId.values();
    }

}
