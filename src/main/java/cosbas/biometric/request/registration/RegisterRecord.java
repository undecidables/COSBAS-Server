package cosbas.biometric.request.registration;

import cosbas.biometric.data.BiometricData;
import org.springframework.data.annotation.Id;

/**
 * Created by Tienie on 13/09/2015.
 */
public class RegisterRecord {

    private String email;
    @Id
    private String personID;
    private BiometricData data;

    public RegisterRecord(String Email, String PersonID, BiometricData data) {
        this.email = Email;
        this.personID = PersonID;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }


    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
