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

    /**
     * Defines a Java Object which stores the user's data as parsed from the POST request
     *
     * @param Email    The user's e-mail address
     * @param PersonID The user's EMPLID
     * @param data     The actual biometric data to persist on the database
     */
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
