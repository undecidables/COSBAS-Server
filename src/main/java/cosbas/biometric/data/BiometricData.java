package cosbas.biometric.data;


import cosbas.biometric.validators.BiometricTypes;
import org.springframework.data.annotation.Id;

/**
 * @author  Renette
 * AccessRecord Request created from http request.
 */
public class BiometricData {
    /**
     * Database ID
     */
    private @Id String id;

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
        this.type = type;
        this.data = data;
    }

    public BiometricData(String personId, BiometricTypes type, byte[] data) {
        this.personID = personId;
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

