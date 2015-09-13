package cosbas.biometric.request.registration;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.access.AccessRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Tienie on 13/09/2015.
 */
public class RegisterRequest extends AccessRequest {
    /**
     * E-mail for the registered person.
     */
    private final String email;
    /**
     * Time request object created on server..
     */
    private final LocalDateTime time = LocalDateTime.now();
    /**
     * Array of biometric data from request.
     */
    private final List<BiometricData> data;

    private final String personID;

    public RegisterRequest(String email, String personID, List<BiometricData> data) {
        super(null,null, data);
        this.email = email;
        this.data = data;
        this.personID = personID;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<BiometricData> getData() {
        return data;
    }

    public String getPersonID() {return personID;}

}
