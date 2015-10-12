package cosbas.notifications;

import cosbas.user.ContactDetail;

/**
 * The Notifications class that will be used to send various notifications
 * Currently the system only allows for email notifications but numerous other methods such as SMS may be used.
 * The Context Class for the Notifications Strategy Pattern
 * @author Vivian Venter
 * @date 10/4/2015.
 */

public class Notifications {

    /**
     * An instance of the strategy currently used by COSBAS
     */
    NotificationsStrategy email = null;


    /**
     * The method that will be used to send the notifications
     * Depending on the strategy used it will call the appropriate function
     */
    public void sendNotifications(ContactDetail contactDetail) {
        if (contactDetail.getType().fromString(contactDetail.getDetails()).equals("EMAIL")) {
            email.sendVisitorNotification(contactDetail);
            email.sendStaffNotification(contactDetail);
        }
    }

    /**
     * Setter for setting the strategy COSBAS will be using i.e Email Notifications
     * @param email - The instance of the Email Concrete Class
     */
    public void setEmail(NotificationsStrategy email) {
        this.email = email;
    }

}
