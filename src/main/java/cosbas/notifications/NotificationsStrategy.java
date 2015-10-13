package cosbas.notifications;

import cosbas.user.ContactDetail;

import java.util.ArrayList;

/**
 * The NotificationsStrategy interface
 * The Strategy Interface for the Notifications Strategy Pattern
 * @author Vivian Venter
 * @date 10/4/2015.
 */

public interface NotificationsStrategy {
   /**
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the visitor to which the email will be send to
    */
   void sendVisitorNotification_Request(ArrayList<ContactDetail> to);


   /**
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Request(ContactDetail to);
}
