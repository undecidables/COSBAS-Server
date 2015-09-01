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
     * Database ID
     */
    private @Id String id;

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * The user with which the biometric data is associated.
     * Does not need to be set for request data but is stored in the database
     */
    private String personID;
    /**
     * Identifies type of biometric data.
     */
    private final BiometricTypes type;
    /**
     * Actual biometric data. This can be an image/access code etc.
     */
    private final byte[] data;


    public BiometricData(BiometricTypes type, byte[] data) {
        this.personID = null;
        this.type = type;
        this.data = data;
    }

    @PersistenceConstructor
    public BiometricData(String personID, BiometricTypes type, byte[] data) {
        this.personID = personID;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getPersonID() {
        return personID;
    }

    public byte[] getData() {
        return data;
    }

    public BiometricTypes getType() {
        return type;
    }

}

