package cosbas.biometric.request.registration;

import cosbas.biometric.data.BiometricData;
import cosbas.user.ContactDetail;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author  Tienie}
 */
public class RegisterRequest {

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

    @Id
    private final String userID;

    /**
     * Defines a Java Object which stores the user's data as parsed from the POST request
     *
     * @param details    The user's contact details
     * @param userID The user's EMPLID
     * @param data     The actual biometric data to persist on the database
     */
    public RegisterRequest(List<ContactDetail> details, String userID, List<BiometricData> data) {
        this.contactDetails = details;
        this.data = data;
        this.userID = userID;
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

    public String getUserID() {return userID;}

    public void merge(RegisterRequest newUser) {
        contactDetails.addAll(newUser.getContactDetails());
        data.addAll(newUser.getData());
    }
}
