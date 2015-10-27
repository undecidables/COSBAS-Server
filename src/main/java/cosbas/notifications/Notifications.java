package cosbas.notifications;

import cosbas.appointment.Appointment;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The Notifications class that will be used to send various notifications
 * Currently the system only allows for email notifications but numerous other methods such as SMS may be used.
 * The Context Class for the Notifications Strategy Pattern
 * @author Vivian Venter
 * @date 10/4/2015.
 */
@Service
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
     * The method that will be used to send the notifications to the visitors
     * Depending on the strategy used it will call the appropriate notification function
     * @param contactDetailsVisitor - the contact details of the visitor(s)
     * @param type - the type of notification to send (See NotificationType above)
     * @param visitorIDs -  the name(s) of the visitor(s)
     * @param tempAppointment - the appointment object to extract the necessary details
     * @param staffCancelled - a boolean value to indicate if the appointment has been cancelled by the staff member
     */
    public void sendVisitorNotifications(ArrayList<ContactDetail> contactDetailsVisitor, NotificationType type, List<String> visitorIDs, Appointment tempAppointment, boolean staffCancelled) {
        switch (type) {
            case REQUEST_APPOINTMENT:
                    email.sendVisitorNotification_Request(contactDetailsVisitor, visitorIDs, tempAppointment);
                break;

            case APPROVE_APPOINTMENT:
                    email.sendVisitorNotification_Approve(contactDetailsVisitor, tempAppointment);
                break;

            case CANCEL_APPOINTMENT:
                    email.sendVisitorNotification_Cancel(contactDetailsVisitor, tempAppointment, staffCancelled);
                break;
            case DENY_APPOINTMENT:
                    email.sendVisitorNotification_Deny(contactDetailsVisitor,tempAppointment);
                break;
        }
    }

    /**
     * The method that will be used to send the notifications to the staff member
     * Depending on the strategy used it will call the appropriate notification function
     * @param contactDetailStaff - the contact details of the staff member
     * @param type - the type of notification to send (See NotificationType above)
     * @param visitorIDs -  the name(s) of the visitor(s)
     * @param tempAppointment - the appointment object to extract the necessary details
     * @param staffCancelled - a boolean value to indicate if the appointment has been cancelled by the staff member
     */
    public void sendStaffNotifications(ArrayList<ContactDetail> contactDetailsVisitor, ArrayList<ContactDetail> contactDetailStaff, NotificationType type, List<String> visitorIDs, Appointment tempAppointment, boolean staffCancelled) {
        switch (type) {
            case REQUEST_APPOINTMENT:
                email.sendStaffNotification_Request(contactDetailStaff,visitorIDs,tempAppointment);
                break;

            case APPROVE_APPOINTMENT:
                email.sendStaffNotification_Approve(contactDetailStaff,contactDetailsVisitor,tempAppointment);
                break;

            case CANCEL_APPOINTMENT:
                email.sendStaffNotification_Cancel(contactDetailStaff,contactDetailsVisitor,tempAppointment,staffCancelled);
                break;
            case DENY_APPOINTMENT:
                email.sendStaffNotification_Deny(contactDetailStaff,contactDetailsVisitor,tempAppointment);
                break;

            case REGISTRATION:
                email.sendStaffNotification_Registration(contactDetailStaff);
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
