package cosbas.notifications;

import cosbas.appointment.Appointment;
import cosbas.biometric.data.TemporaryAccessCode;
import cosbas.user.ContactDetail;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The Email class to send the email notifications to the visitor and staff member
 * The Concrete Class for the Notifications Strategy Pattern
 * @author Vivian Venter
 * @date 10/4/2015.
 */

public class Email implements NotificationsStrategy  {

    /**
     * The instance of the MailSender class that spring uses to send the emails
     */
    private static MailSender mailSender;

    /**
     * The instance of the SimpleMailMessage that uses the visitor template specified in the beans.xml document
     * This template is only for the email to the visitors
     */
    private static SimpleMailMessage visitorTemplateMessageRequest;
    private static SimpleMailMessage visitorTemplateMessageApprove;
    private static SimpleMailMessage visitorTemplateMessageCancel;
    private static SimpleMailMessage visitorTemplateMessageDeny;

    /**
     * The instance of the SimpleMailMessage that uses the staff template specified in the beans.xml document
     * This template is only for the email to the staff
     */
    private static SimpleMailMessage staffTemplateMessageRequest;
    private static SimpleMailMessage staffTemplateMessageApprove;
    private static SimpleMailMessage staffTemplateMessageCancel;
    private static SimpleMailMessage staffTemplateMessageDeny;
    private static SimpleMailMessage staffTemplateMessageReg;

    /**
     * The method to send the appointment request notification to the visitor(s)
     * The template method as specified in the Strategy Interface
     * @param visitors - The email address(es) of the visitor(s) to which the email will be send to
     * @param visitorID - The name(s) of the visitor(s)
     * @param tempAppointment - the appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendVisitorNotification_Request(ContactDetail visitors, String visitorID, Appointment tempAppointment) {
        setProperties();
        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessageRequest);

        notification.setTo(visitors.getDetails());

        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());
        notification.setText(
                "Dear " + visitorID +"\n\n" +
                "Your request for an appointment with " + tempAppointment.getStaffID() + " has been sent for approval.\n\n" +

                "Request Review:\n" +
                "Appointment ID: " + tempAppointment.getId() + "\n" +
                "Appointment with: " + tempAppointment.getStaffID() + "\n" +
                "Date/Time: " + displayDate + "\n" +
                "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                "Reason: " + tempAppointment.getReason() + "\n\n" +

                "Once confirmation of approval has been received, \n" +
                "you will receive your access code with which you will gain \n" +
                "access to the department where the office of \n" +
                tempAppointment.getStaffID() +
                " is located.\n\n" +

                "Regards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R1");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }

    }

    /**
     * The method to send the appointment request notification to the staff member
     * The template method as specified in the Strategy Interface
     * @param emails - The email address of the staff member to which the email will be send to
     * @param visitors - The email address(ess) of the visitors
     * @param tempAppointment - the appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendStaffNotification_Request(ArrayList<ContactDetail> emails, ArrayList<ContactDetail> visitors, Appointment tempAppointment) {
        setProperties();

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageRequest);

        String[] to = getStaffEmails(emails);

        notification.setTo(to);

        StringBuilder visitorEmails = getVisitorEmails(visitors);

        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());
        notification.setText(
                "Dear " + tempAppointment.getStaffID() + "\n\n" +
                "A request for an appointment with you has been received for your approval.\n\n" +

                "Request Review:\n" +
                "Appointment ID: " + tempAppointment.getId() + "\n" +
                "Appointment with: "+ tempAppointment.getStaffID() + "\n" +
                "Date/Time: " + displayDate + "\n" +
                "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                "Reason: " + tempAppointment.getReason() + "\n\n" +
                "Client: \n" +
                visitorEmails.toString() +
                "\n\n" +

                "Please log into your COSBAS account, go to \n" +
                "appointments then choose approve or deny,\n" +
                "to approve or deny the request.\n\n" +

                "Regards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R2");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment approved notification to the visitor(s)
     * The template method as specified in the Strategy Interface
     * @param visitor - The email address(es) of the visitor(s) to which the email will be send to
     * @param tempAppointment - The appointment object to extract the necessary details of the appointment
     * @param codes - The access codes for each visitor
     */
    @Override
    public void sendVisitorNotification_Approve(ContactDetail visitor, Appointment tempAppointment, List<TemporaryAccessCode> codes) {
        setProperties();
        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessageApprove);

        notification.setTo(visitor.getDetails());

        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());

        notification.setText(
                "Dear User\n\n" +
                        "We have good news for you! Your request for an \n" +
                        "appointment has been approved.\n\n" +

                        "Your access code to the department is: \n" +
                        "\t\t" +
                        codes.toString() + "\n\n" +

                        "Appointment Review:\n" +
                        "Appointment ID: " + tempAppointment.getId() + "\n" +
                        "Appointment with: " + tempAppointment.getStaffID() + "\n" +
                        "Date/Time: " + displayDate + "\n" +
                        "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                        "Reason: " + tempAppointment.getReason() + "\n\n" +

                        "Please note: \n" +
                        "Your access code is only valid for \n" +
                        "the time of the approved appointment.\n\n" +
                        "You are able to cancel this appointment by going\n" +
                        "to appointment, cancel appointment and typing\n" +
                        "in the appointmentID as given above.\n\n" +

                        "Regards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R3");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment approved notification to the staff member
     * The template method as specified in the Strategy Interface
     * @param emails - The email address of the staff member to which the email will be send to
     * @param visitors - The email address(ess) of the visitor(s)
     * @param tempAppointment - The appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendStaffNotification_Approve(ArrayList<ContactDetail> emails, ArrayList<ContactDetail> visitors, Appointment tempAppointment) {
        setProperties();

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageApprove);

        String[] to = getStaffEmails(emails);

        StringBuilder visitorEmails = getVisitorEmails(visitors);

        notification.setTo(to);

        notification.setText(
                "Dear " + tempAppointment.getStaffID() + "\n\n" +
                       "You have successfully approved the appointment with:\n" +
                        visitorEmails.toString() +

                        "\n\nYou are able to cancel this appointment by going\n" +
                        "to appointment, cancel appointment and typing\n" +
                        "in the appointmentID given below.\n\n" +
                        "AppointmentID: " + tempAppointment.getId() +
                        "\n\n" +

                        "Regards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R4");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment cancelled notification to the visitor(s)
     * The template method as specified in the Strategy Interface
     * @param visitor - The email address(es) of the visitor(s) to which the email will be send to
     * @param tempAppointment - The appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendVisitorNotification_Cancel(ContactDetail visitor, Appointment tempAppointment, boolean staffCancelled) {
        setProperties();
        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessageCancel);

        notification.setTo(visitor.getDetails());

        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());

        if(!staffCancelled) {
            notification.setText(
                    "Dear User\n\n" +
                            "An appointment has been cancelled by you.\n\n" +

                            "Appointment Cancelled:\n" +
                            "Appointment ID: " + tempAppointment.getId() + "\n" +
                            "Appointment with: " + tempAppointment.getStaffID() + "\n" +
                            "Date/Time: " + displayDate + "\n" +
                            "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                            "Reason: " + tempAppointment.getReason() + "\n\n" +

                            "You can request a new appointment at any time.\n\n" +

                            "Regards,\nCOSBAS"
            );
        }
        else {
            notification.setText(
                    "Dear User\n\n" +
                            "We have some unfortunate news for you.\n" +
                            "An appointment has been cancelled.\n\n" +

                            "Appointment Cancelled:\n" +
                            "Appointment ID: " + tempAppointment.getId() + "\n" +
                            "Appointment with: " + tempAppointment.getStaffID() + "\n" +
                            "Date/Time: " + displayDate + "\n" +
                            "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                            "Reason: " + tempAppointment.getReason() + "\n\n" +

                            "You can request a new appointment at any time.\n\n" +

                            "Regards,\nCOSBAS"
            );
        }
        try {
            mailSender.send(notification);
            System.out.println("Email Send! R5");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment cancelled notification to the staff member
     * The template method as specified in the Strategy Interface
     * @param emails - The email address of the staff member to which the email will be send to
     * @param visitors - The name(s) of the visitor(s)
     * @param tempAppointment - The appointment object to extract the necessary details of the appointment
     * @param staffCancelled - A boolean value to indicate if the appointment has been cancelled by the staff member
     */
    @Override
    public void sendStaffNotification_Cancel(ArrayList<ContactDetail> emails, ArrayList<ContactDetail> visitors, Appointment tempAppointment, boolean staffCancelled) {
        setProperties();

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageCancel);

        String[] to = getStaffEmails(emails);

        notification.setTo(to);

        StringBuilder visitorEmails = getVisitorEmails(visitors);


        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());
        if (staffCancelled) {
            notification.setText(
                    "Dear " + tempAppointment.getStaffID() + "\n\n" +
                            "You have successfully cancelled and removed the appointment with:\n" +
                            visitorEmails.toString() +

                            "\n\nRegards,\nCOSBAS"
            );
        }
        else {
            notification.setText(
                    "Dear " + tempAppointment.getStaffID() + "\n\n" +
                            "An appointment that is scheduled with you\n" + "" +
                            "has been cancelled and removed from your calendar.\n\n" +

                            "Appointment Cancelled:\n" +
                            "Appointment ID: " + tempAppointment.getId() + "\n" +
                            "Appointment with: "+ tempAppointment.getStaffID() + "\n" +
                            "Date/Time: " + displayDate + "\n" +
                            "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                            "Reason: " + tempAppointment.getReason() + "\n\n" +
                            "Client: \n" +
                            visitorEmails.toString() +

                            "\n\nRegards,\nCOSBAS"
            );
        }

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R6");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment denied notification to the visitor(s)
     * The template method as specified in the Strategy Interface
     * @param visitor - The email address(es) of the visitor(s) to which the email will be send to
     * @param tempAppointment - the appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendVisitorNotification_Deny(ContactDetail visitor, Appointment tempAppointment) {
        setProperties();
        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessageDeny);

        notification.setTo(visitor.getDetails());

        String displayDate = getDateTimeDisplay(tempAppointment.getDateTime());

        notification.setText(
                "Dear User\n\n" +
                        "We have some unfortunate news for you. An appointment \n" +
                        "requested by you has been denied.\n\n" +

                        "Appointment Denied:\n" +
                        "Appointment ID: " + tempAppointment.getId() + "\n" +
                        "Appointment with: " + tempAppointment.getStaffID() + "\n" +
                        "Date/Time: " + displayDate + "\n" +
                        "Duration: " + tempAppointment.getDurationMinutes() + " minutes\n" +
                        "Reason: " + tempAppointment.getReason() + "\n\n" +

                        "Feel free to request a new appointment at any time.\n\n" +

                        "Regards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R7");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the appointment denied notification to the staff member
     * The template method as specified in the Strategy Interface
     * @param emails - The email address of the staff member to which the email will be send to
     * @param visitors - The name(s) of the visitor(s)
     * @param tempAppointment - The appointment object to extract the necessary details of the appointment
     */
    @Override
    public void sendStaffNotification_Deny(ArrayList<ContactDetail> emails, ArrayList<ContactDetail> visitors, Appointment tempAppointment) {
        setProperties();

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageDeny);

        String[] to = getStaffEmails(emails);

        notification.setTo(to);

        StringBuilder visitorEmails = getVisitorEmails(visitors);

        notification.setText(
                "Dear " + tempAppointment.getStaffID() + "\n\n" +
                        "You have successfully denied the appointment with:\n" +
                        visitorEmails.toString() +

                        "\n\nRegards,\nCOSBAS"
        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R8");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method to send the registration notification to the staff member
     * The template method as specified in the Strategy Interface
     * @param emails - The email address of the staff member that has been registered to the COSBAS System
     */
    @Override
    public void sendStaffNotification_Registration(ArrayList<ContactDetail> emails) {
        setProperties();

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageReg);

        String[] to = getStaffEmails(emails);

        notification.setTo(to);

        notification.setText(
            "Dear Staff Member,\n\n" +

                    "We are so pleased to inform you of your successful \n" +
                    "registration on the COSBAS access control system.\n\n" +

                    "Your access to the department will now be more seamless\n" +
                    "and much more secure." +

                    "\n\nRegards,\nCOSBAS"

        );

        try {
            mailSender.send(notification);
            System.out.println("Email Send! R9");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Setter function to set the mailSender (Used by beans.xml)
     * @param mailSender - An instance of the mailSender
     */
    public void setMailSender(MailSender mailSender) {
        Email.mailSender = mailSender;
    }

    /**
     * Setter function to set the visitorTemplateMessage (Used by beans.xml)
     * @param visitorTemplateMessage - An instance of the visitorTemplateMessage
     */
    public void setVisitorTemplateMessageRequest(SimpleMailMessage visitorTemplateMessage) {
        Email.visitorTemplateMessageRequest = visitorTemplateMessage;
    }

    /**
     * Setter function to set the staffTemplateMessage (Used by beans.xml)
     * @param staffTemplateMessage - An instance of the staffTemplateMessage
     */
    public void setStaffTemplateMessageRequest(SimpleMailMessage staffTemplateMessage) {
        Email.staffTemplateMessageRequest = staffTemplateMessage;
    }

    /**
     * Setter function to set the staffTemplateMessageApprove (Used by beans.xml)
     * @param staffTemplateMessageApprove - An instance of the staffTemplateMessageApprove
     */
    public void setStaffTemplateMessageApprove(SimpleMailMessage staffTemplateMessageApprove) {
        Email.staffTemplateMessageApprove = staffTemplateMessageApprove;
    }

    /**
     * Setter function to set the visitorTemplateMessageApprove (Used by beans.xml)
     * @param visitorTemplateMessageApprove - An instance of the visitorTemplateMessageApprove
     */
    public void setVisitorTemplateMessageApprove(SimpleMailMessage visitorTemplateMessageApprove) {
        Email.visitorTemplateMessageApprove = visitorTemplateMessageApprove;
    }

    /**
     * Setter function to set the visitorTemplateMessageCancel (Used by beans.xml)
     * @param visitorTemplateMessageCancel - An instance of the visitorTemplateMessageCancel
     */
    public void setVisitorTemplateMessageCancel(SimpleMailMessage visitorTemplateMessageCancel) {
        Email.visitorTemplateMessageCancel = visitorTemplateMessageCancel;
    }

    /**
     * Setter function to set the staffTemplateMessageCancel (Used by beans.xml)
     * @param staffTemplateMessageCancel - An instance of the staffTemplateMessageCancel
     */
    public void setStaffTemplateMessageCancel(SimpleMailMessage staffTemplateMessageCancel) {
        Email.staffTemplateMessageCancel = staffTemplateMessageCancel;
    }

    /**
     * Setter function to set the visitorTemplateMessageDeny (Used by beans.xml)
     * @param visitorTemplateMessageDeny - An instance of the visitorTemplateMessageDeny
     */
    public void setVisitorTemplateMessageDeny(SimpleMailMessage visitorTemplateMessageDeny) {
        Email.visitorTemplateMessageDeny = visitorTemplateMessageDeny;
    }

    /**
     * Setter function to set the staffTemplateMessageDeny (Used by beans.xml)
     * @param staffTemplateMessageDeny - An instance of the staffTemplateMessageDeny
     */
    public void setStaffTemplateMessageDeny(SimpleMailMessage staffTemplateMessageDeny) {
        Email.staffTemplateMessageDeny = staffTemplateMessageDeny;
    }

    /**
     * Setter function to set the staffTemplateMessageReg (Used by beans.xml)
     * @param staffTemplateMessageReg - An instance of the staffTemplateMessageReg
     */
    public void setStaffTemplateMessageReg(SimpleMailMessage staffTemplateMessageReg) {
        Email.staffTemplateMessageReg = staffTemplateMessageReg;
    }

    /**
     * The function that sets the starttls properties of the COSBAS Gmail Account
     */
    private void setProperties() {
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.STARTTLS.enable","true");
        emailProps.put("mail.smtp.auth", "true");
    }

    /**
     * The function that converts the ContactDetail objects of the staff member and change it into a String[]
     * @param staff- the ArrayList of ContactDetails objects
     * @return to - A String[] that contains only the email address(es) of the staff member
     */
    private String[] getStaffEmails(ArrayList<ContactDetail> staff) {
        String[] to = new String[staff.size()];

        for (int i = 0; i < staff.size(); i++) {
            to[i] = staff.get(i).getDetails();
        }
        return to;
    }

    /**
     * The function that converts the ContactDetail objects of the visitor and change it into a String[]
     * @param visitors - the ArrayList of ContactDetails objects
     * @return to - A String[] that contains only the email address(es) of the visitor
     */
    private StringBuilder getVisitorEmails(ArrayList<ContactDetail> visitors) {
        StringBuilder visitorEmails = new StringBuilder();

        for (ContactDetail c: visitors) {
            visitorEmails.append(c.getDetails()).append("\n");
        }
        return visitorEmails;
    }

    /**
     * A function that formats the output of the date and time
     * @param dateTime - the LocalDateTime object that represents the date/time of the appointment
     * @return String - the date/time in the format "yyyy-mm-dd at hh:mm:ss"
     */
    private String getDateTimeDisplay(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime().minusSeconds(30);
        return date + " at " + time;
    }
}
