package cosbas.user;

import org.springframework.data.annotation.Id;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@author Renette}
 */
public class User {

    @Id
    private final String userID;

    /**
     * All contact details of the user. Using a set to keep it unique.
     */
    private final Set<ContactDetail> contact;

    public User(String userID, Set<ContactDetail> contact) {
        this.userID = userID;
        this.contact = contact;
    }

    public User(String userID) {
        this.userID = userID;
        this.contact = new HashSet<>();
    }

    public Collection<ContactDetail> getContact() {
        return contact;
    }

    private boolean addContactDetail(ContactDetail c) {
        return this.contact.add(c);
    }
    private boolean addContactDetails(Collection<ContactDetail> c ) {
        return contact.addAll(c);
    }
}
