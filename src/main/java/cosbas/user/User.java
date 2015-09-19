package cosbas.user;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

/**
 * {@author Renette}
 */
public class User {

    @Id
    private final String userID;
    private final List<ContactDetail> contact;

    public User(String userID, List<ContactDetail> contact) {
        this.userID = userID;
        this.contact = contact;
    }

    public User(String userID) {
        this.userID = userID;
        this.contact = new LinkedList<>();
    }

    public List<ContactDetail> getContact() {
        return contact;
    }

    private boolean addContactDetail(ContactDetail c) {
        return this.contact.add(c);
    }
}
