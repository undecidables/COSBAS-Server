package cosbas.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * {@author Renette}
 */
@Service
public class UserManager {

    public UserManager() {
    }

    @Autowired
    UserDAO userRepository;

    public void addContactDetail(String userID, ContactDetail detail) {
        addContactDetails(userID, Collections.singletonList(detail));
    }

    public void addContactDetails(String userID, Collection<ContactDetail> detailCollections) {
        User u = userRepository.findOne(userID);

        if (u == null) {
            u = new User(userID);
            u.addContactDetails(detailCollections);
        }
        else {
            u.addContactDetails(detailCollections);
        }

        userRepository.save(u);
    }

    public void removeContactDetail(String userID, ContactDetail detail) throws NoSuchUserException {
        User u = userRepository.findOne(userID);

        if (u == null) {
            throw new NoSuchUserException(userID + " does not have any contact details to remove.");
        }

        u.removeContactDetail(detail);

        if (u.getContact().isEmpty()) {
            userRepository.delete(u);
        } else {
            userRepository.save(u);
        }
    }

}
