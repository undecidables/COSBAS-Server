package cosbas.biometric.data;


import cosbas.biometric.BiometricTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@author  Renette Ros}
 * AccessRecord Request created from http request.
 */
public class BiometricData {
    /**
     * Identifies type of biometric data.
     */
    private final BiometricTypes type;
    /**
     * Actual biometric data. This can be an image/access code etc.
     */
    private final byte[] data;
    /**
     * Database ID
     */
    private @Id String id;
    /**
     * The user with which the biometric data is associated.
     * Does not need to be set for request data but is stored in the database
     */
    private String userID;

    public BiometricData(BiometricTypes type, byte[] data) {
        this(null, type, data);
    }


    @PersistenceConstructor
    public BiometricData(String userID, BiometricTypes type, byte[] data) {
        this.userID = userID;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public byte[] getData() {
        return data;
    }

    public BiometricTypes getType() {
        return type;
    }

}

