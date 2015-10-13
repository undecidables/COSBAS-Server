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
    * The method to send the appointment request notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    */
   void sendVisitorNotification_Request(ArrayList<ContactDetail> to);

   /**
    * The method to send the appointment request notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Request(ContactDetail to);

   /**
    * The method to send the appointment approve notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    */
   void sendVisitorNotification_Approve(ArrayList<ContactDetail> to);


   /**
    * The method to send the appointment approve notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Approve(ContactDetail to);

   /**
    * The method to send the appointment cancel notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    */
   void sendVisitorNotification_Cancel(ArrayList<ContactDetail> to);

   /**
    * The method to send the appointment cancel notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Cancel(ContactDetail to);

   /**
    * The method to send the appointment deny notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    */
   void sendVisitorNotification_Deny(ArrayList<ContactDetail> to);

   /**
    * The method to send the appointment deny notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Deny(ContactDetail to);

   /**
    * The method to send the registration completed notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Registration(ContactDetail to);
}
