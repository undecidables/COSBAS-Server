package cosbas.permissions;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@author Renette}
 */
public class Permission {
    @Id
    private String id;

    @PersistenceConstructor
    protected Permission(String id, String userID, PermissionId permission) {
        this.id = id;
        this.userID = userID;
        this.permission = permission;
    }

    public Permission(String userID, PermissionId permission) {
        this.id = buildId(userID, permission);
        this.userID = userID;
        this.permission = permission;
    }

    /**
     * Creates an id for the object to ensure there are no duplicates in the database.
     * Duplicate = userId and permission is the same.
     * @param userID
     * @param permission
     * @return
     */
    private String buildId(String userID, PermissionId permission) {
        return userID + "_" + permission.toString();
    }

    public String toString() {
        return userID + " can " + permission;
    }

    public PermissionId getPermission(){
        return permission;
    }
    private String userID;
    private PermissionId permission;
}
