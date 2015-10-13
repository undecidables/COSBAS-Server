package cosbas.notifications;

import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;

import java.util.ArrayList;

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
     * Enumeration type to specify which type of notification has to be send
     */
    public enum NotificationType {
        REQUEST_APPOINTMENT,
        APPROVE_APPOINTMENT,
        CANCEL_APPOINTMENT,
        DENY_APPOINTMENT,
        REGISTRATION
    }

    /**
     * The method that will be used to send the notifications
     * Depending on the strategy used it will call the appropriate notification function
     * @param contactDetailsVisitor - the contact details of the visitor(s)
     * @param contactDetailStaff - the contact details of the staff member
     * @param type - the type of notification to send (See NotificationType above)
     */
    public void sendNotifications(ArrayList<ContactDetail> contactDetailsVisitor, ContactDetail contactDetailStaff, NotificationType type) {
        switch (type) {
            case REQUEST_APPOINTMENT:
                if(contactDetailStaff.getType().equals(ContactTypes.EMAIL)) { //Renette is dit reg hoe ek kyk vir die type?
                    email.sendVisitorNotification_Request(contactDetailsVisitor);
                    email.sendStaffNotification_Request(contactDetailStaff);
                }
                break;

            case APPROVE_APPOINTMENT:
                if(contactDetailStaff.getType().equals(ContactTypes.EMAIL)) { //Renette is dit reg hoe ek kyk vir die type?
                    email.sendVisitorNotification_Approve(contactDetailsVisitor);
                    email.sendStaffNotification_Approve(contactDetailStaff);
                }
                break;

            case CANCEL_APPOINTMENT:
                if(contactDetailStaff.getType().equals(ContactTypes.EMAIL)) {
                    email.sendVisitorNotification_Cancel(contactDetailsVisitor);
                    email.sendStaffNotification_Cancel(contactDetailStaff);
                }
                break;
            case DENY_APPOINTMENT:
                if(contactDetailStaff.getType().equals(ContactTypes.EMAIL)) {
                    email.sendVisitorNotification_Deny(contactDetailsVisitor);
                    email.sendStaffNotification_Deny(contactDetailStaff);
                }
                break;

            case REGISTRATION:
                if(contactDetailStaff.getType().equals(ContactTypes.EMAIL)) {
                    email.sendStaffNotification_Registration(contactDetailStaff);
                }
                break;
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
