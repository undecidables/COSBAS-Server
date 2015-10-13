package cosbas.notifications;

import cosbas.user.ContactDetail;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
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
     * The template method as specified in the Strategy Interface
     * Function that will send the email to the appropriate visitor
     * @param visitors - The email address of the visitor to which the email will be send to
     */
    @Override
    public void sendVisitorNotification_Request(ArrayList<ContactDetail> visitors) {
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.STARTTLS.enable","true");
        emailProps.put("mail.smtp.auth","true");
        System.out.println("HERE1");

        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessageRequest);

        String[] to = new String[visitors.size()];

        int i = 0;
        for (ContactDetail contact: visitors) {
            to[i] = contact.getDetails();
            i++;
        }

        notification.setTo(to);
        System.out.println("HERE2");

        //We can still add the necessary info here
        notification.setText(
                "Dear User\n" +
                        "Your request for an appointment with #staffName has been sent for approval.\n\n" +

                        "Request Review:" +
                        "Appointment ID: #AppointmentID\n" +
                        "Apointment with: #staffName\n" +
                        "Time: #startTime\n" +
                        "Duration: #Duration\n" +
                        "Reason: #Reason\n\n" +

                        "Once confirmation of approval has been received, \n" +
                        "you will receive your access code with which you will gain \n" +
                        "access to the department where the office of \n" +
                        "#staffName is located.\n\n" +

                        "Regards,\nCOSBAS"
        );

        try {
            System.out.println("HERE3");
            mailSender.send(notification);
            System.out.println("Email Send!");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendStaffNotification_Request(ContactDetail to) {
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.STARTTLS.enable","true");
        emailProps.put("mail.smtp.auth","true");
        System.out.println("HERE11");

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessageRequest);
        notification.setTo(to.getDetails());
        System.out.println("HERE22");

        //We can still add the necessary info here
        notification.setText(
                "Dear Staff Member\n" +
                        "A request for an appointment with you has been received for your approval.\n\n" +

                        "Request review:\n" +
                        "Appointment ID: #AppointmentID\n" +
                        "Apointment with: #staffName\n" +
                        "Time: #startTime\n" +
                        "Duration: #Duration\n" +
                        "Reason: #Reason\n" +
                        "Client: #clientEmails[]\n\n" +

                        "Please log into your COSBAS account, go to \n" +
                        "appointments then choose approve or deny,\n" +
                        "to approve or deny the request.\n\n" +

                        "Regards,\nCOSBAS"
        );

        try {
            System.out.println("HERE33");
            mailSender.send(notification);
            System.out.println("Email Send!");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendVisitorNotification_Approve(ArrayList<ContactDetail> to) {

    }

    @Override
    public void sendStaffNotification_Approve(ContactDetail to) {

    }

    @Override
    public void sendVisitorNotification_Cancel(ArrayList<ContactDetail> to) {

    }

    @Override
    public void sendStaffNotification_Cancel(ContactDetail to) {

    }

    @Override
    public void sendVisitorNotification_Deny(ArrayList<ContactDetail> to) {

    }

    @Override
    public void sendStaffNotification_Deny(ContactDetail to) {

    }

    @Override
    public void sendStaffNotification_Registration(ContactDetail to) {

    }

    /**
     * Setter function to set the mailSender (Used by beans.xml)
     * @param mailSender - An instance of the mailSender
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Setter function to set the visitorTemplateMessage (Used by beans.xml)
     * @param visitorTemplateMessage - An instance of the visitorTemplateMessage
     */
    public void setVisitorTemplateMessageRequest(SimpleMailMessage visitorTemplateMessage) {
        this.visitorTemplateMessageRequest = visitorTemplateMessage;
    }

    /**
     * Setter function to set the staffTemplateMessage (Used by beans.xml)
     * @param staffTemplateMessage - An instance of the staffTemplateMessage
     */
    public void setStaffTemplateMessageRequest(SimpleMailMessage staffTemplateMessage) {
        this.staffTemplateMessageRequest = staffTemplateMessage;
    }

    public void setStaffTemplateMessageApprove(SimpleMailMessage staffTemplateMessageApprove) {
        this.staffTemplateMessageApprove = staffTemplateMessageApprove;
    }

    public void setVisitorTemplateMessageApprove(SimpleMailMessage visitorTemplateMessageApprove) {
        this.visitorTemplateMessageApprove = visitorTemplateMessageApprove;
    }

    public void setVisitorTemplateMessageCancel(SimpleMailMessage visitorTemplateMessageCancel) {
        this.visitorTemplateMessageCancel = visitorTemplateMessageCancel;
    }

    public void setStaffTemplateMessageCancel(SimpleMailMessage staffTemplateMessageCancel) {
        this.staffTemplateMessageCancel = staffTemplateMessageCancel;
    }

    public void setVisitorTemplateMessageDeny(SimpleMailMessage visitorTemplateMessageDeny) {
        this.visitorTemplateMessageDeny = visitorTemplateMessageDeny;
    }

    public void setStaffTemplateMessageDeny(SimpleMailMessage staffTemplateMessageDeny) {
        this.staffTemplateMessageDeny = staffTemplateMessageDeny;
    }

    public void setStaffTemplateMessageReg(SimpleMailMessage staffTemplateMessageReg) {
        this.staffTemplateMessageReg = staffTemplateMessageReg;
    }
}
