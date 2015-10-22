package cosbas.permissions;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@author Renette}
 */
public class Permission {
    @Id
    private String id;
    private String userID;
    private PermissionId permission;

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
     *
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Permission other = (Permission) object;
        return new EqualsBuilder()
                .append(userID, other.userID)
                .append(permission, other.permission)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 97)
                .append(userID)
                .append(permission)
                .toHashCode();
    }
}
