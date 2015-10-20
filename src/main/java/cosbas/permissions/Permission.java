package cosbas.permissions;

import org.springframework.data.annotation.Id;

/**
 * {@author Renette}
 */
public class Permission {
    @Id
    String id;
    String userID;
    PermissionId permission;
}
