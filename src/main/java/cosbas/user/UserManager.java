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
    private final UserDAO userRepository;

    @Autowired
    public UserManager(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    public User addContactDetail(String userID, ContactDetail detail) {
        return addContactDetails(userID, Collections.singletonList(detail));
    }

    public User addContactDetails(String userID, Collection<ContactDetail> detailCollections) {
        User u = userRepository.findOne(userID);

        if (u == null) {
            u = new User(userID);
            u.addContactDetails(detailCollections);
        }
        else {
            u.addContactDetails(detailCollections);
        }

        userRepository.save(u);
        return u;
    }

    public void removeContactDetail(String userID, ContactDetail detail) {
        User u = userRepository.findOne(userID);

        if (u != null) {

            u.removeContactDetail(detail);

            if (u.getContact().isEmpty()) {
                userRepository.delete(u);
            } else {
                userRepository.save(u);
            }
        }
    }

    public void updateDetails(String userID, ContactDetail detail) {
        User u = userRepository.findOne(userID);

        if (u != null) {

           u.updateContactDetail(detail.getType(), detail);
        }

        userRepository.save(u);

    }

    public User getUser(String userID) {
        return userRepository.findOne(userID);
    }

}
