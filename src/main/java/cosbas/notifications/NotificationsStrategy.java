package cosbas.notifications;

import cosbas.appointment.Appointment;
import cosbas.biometric.data.TemporaryAccessCode;
import cosbas.user.ContactDetail;

import java.util.ArrayList;

/**
 * The NotificationsStrategy interface
 * The Strategy Interface for the Notifications Strategy Pattern
 * @author Vivian Venter
 */

public interface NotificationsStrategy {
   /**
    * The method to send the appointment request notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    * @param visitorNames - The name of the visitor
    */
     void sendVisitorNotification_Request(ContactDetail to, Appointment tempAppointment, String visitorNames);

   /**
    * The method to send the appointment request notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    * @param visitors - The email address(es) of the visitors
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    */
   void sendStaffNotification_Request(ArrayList<ContactDetail> to, ArrayList<ContactDetail> visitors, Appointment tempAppointment);

   /**
    * The method to send the appointment approve notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    * @param codes - The access code for the appointment
    * @param visitorNames - The name of the visitor
    */
   void sendVisitorNotification_Approve(ContactDetail to, Appointment tempAppointment, TemporaryAccessCode codes, String visitorNames);


   /**
    * The method to send the appointment approve notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    */
   void sendStaffNotification_Approve(ArrayList<ContactDetail> to, ArrayList<ContactDetail> visitorIDs, Appointment tempAppointment);

   /**
    * The method to send the appointment cancel notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    * @param staffCancelled - A boolean value to indicate if the appointment has been cancelled by the staff member
    * @param visitorNames - The name of the visitor
    */
   void sendVisitorNotification_Cancel(ContactDetail to, Appointment tempAppointment, boolean staffCancelled, String visitorNames);

   /**
    * The method to send the appointment cancel notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    * @param staffCancelled - A boolean value to indicate if the appointment has been cancelled by the staff member
    */
   void sendStaffNotification_Cancel(ArrayList<ContactDetail> to, ArrayList<ContactDetail> visitorIDs, Appointment tempAppointment, boolean staffCancelled);

   /**
    * The method to send the appointment deny notification to the visitor(s)
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address(es) of the visitor(s) to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    * @param visitorNames - The name of the visitor
    */
   void sendVisitorNotification_Deny(ContactDetail to, Appointment tempAppointment, String visitorNames);

   /**
    * The method to send the appointment deny notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    * @param tempAppointment - The appointment object to extract the necessary details of the appointment
    */
   void sendStaffNotification_Deny(ArrayList<ContactDetail> to, ArrayList<ContactDetail> visitorIDs, Appointment tempAppointment);

   /**
    * The method to send the registration completed notification to the staff member
    * The template method to be overridden by the Concrete Classes
    * @param to - The email address of the staff member to which the email will be send to
    */
   void sendStaffNotification_Registration(ArrayList<ContactDetail> to);
}
