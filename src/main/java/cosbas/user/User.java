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
    private final List<ContactDetails> contact;

    public User(String userID, List<ContactDetails> contact) {
        this.userID = userID;
        this.contact = contact;
    }

    public User(String userID) {
        this.userID = userID;
        this.contact = new LinkedList<>();
    }

    public List<ContactDetails> getContact() {
        return contact;
    }

    private boolean addContactDetail(ContactDetails c) {
        return this.contact.add(c);
    }
}
