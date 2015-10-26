package cosbas.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.*;

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

    @PersistenceConstructor
    public User(String userID, Set<ContactDetail> contact) {
        this.userID = userID;
        this.contact = contact;
    }

    public User(String userID) {
        this.userID = userID;
        this.contact = new HashSet<>();
    }

    public List<ContactDetail> getContact() {
        List<ContactDetail> temp = new ArrayList<>();
        for (ContactDetail c: contact){
            temp.add(c);
        }
        return temp;
    }

    public boolean addContactDetail(ContactDetail c) {
        return this.contact.add(c);
    }

    public boolean addContactDetails(Collection<ContactDetail> c) {
        return contact.addAll(c);
    }

    public boolean removeContactDetail(ContactDetail c) {
        return this.contact.remove(c);
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return getUserID();
    }

}
