package cosbas.biometric.request.registration;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.user.ContactDetail;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author  Tienie}
 */
public class RegisterRequest extends AccessRequest {

    @Id
    private String id;

    /**
     * E-mail for the registered person.
     */
    private final List<ContactDetail> contactDetails;
    /**
     * Time request object created on server..
     */
    private final LocalDateTime time = LocalDateTime.now();
    /**
     * Array of biometric data from request.
     */
    private final List<BiometricData> data;

    private final String personID;

    /**
     * Defines a Java Object which stores the user's data as parsed from the POST request
     *
     * @param details    The user's contact details
     * @param personID The user's EMPLID
     * @param data     The actual biometric data to persist on the database
     */
    public RegisterRequest(List<ContactDetail> details, String personID, List<BiometricData> data) {
        super(null,null, data);
        this.contactDetails = details;
        this.data = data;
        this.personID = personID;
    }

    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<BiometricData> getData() {
        return data;
    }

    public String getPersonID() {return personID;}

    public String getId() {
        return id;
    }
}
