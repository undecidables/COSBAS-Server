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
    private static SimpleMailMessage visitorTemplateMessage;

    /**
     * The instance of the SimpleMailMessage that uses the staff template specified in the beans.xml document
     * This template is only for the email to the staff
     */
    private static SimpleMailMessage staffTemplateMessage;

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

        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessage);

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
                        "An appointment has been scheduled by you.\n\n" +
                        "Regards,\nCOSBAS System"
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

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessage);
        notification.setTo(to.getDetails());
        System.out.println("HERE22");

        //We can still add the necessary info here
        notification.setText(
                "Dear Staff Member\n" +
                        "An appointment has been scheduled with you.\n\n" +
                        "Regards,\nCOSBAS System"
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
    public void sendStaffNotification_Registration(ContactDetail to) {
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.STARTTLS.enable","true");
        emailProps.put("mail.smtp.auth","true");
        System.out.println("HERE111");

        SimpleMailMessage notification = new SimpleMailMessage(staffTemplateMessage);
        notification.setTo(to.getDetails());
        System.out.println("HERE222");

        //We can still add the necessary info here
        notification.setText(
                "Dear Staff Member\n" +
                        "An appointment has been scheduled with you.\n\n" +
                        "Regards,\nCOSBAS System"
        );

        try {
            System.out.println("HERE333");
            mailSender.send(notification);
            System.out.println("Email Send!");
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
        this.mailSender = mailSender;
    }

    /**
     * Setter function to set the visitorTemplateMessage (Used by beans.xml)
     * @param visitorTemplateMessage - An instance of the visitorTemplateMessage
     */
    public void setVisitorTemplateMessage(SimpleMailMessage visitorTemplateMessage) {
        this.visitorTemplateMessage = visitorTemplateMessage;
    }

    /**
     * Setter function to set the staffTemplateMessage (Used by beans.xml)
     * @param staffTemplateMessage - An instance of the staffTemplateMessage
     */
    public void setStaffTemplateMessage(SimpleMailMessage staffTemplateMessage) {
        this.staffTemplateMessage = staffTemplateMessage;
    }

}
