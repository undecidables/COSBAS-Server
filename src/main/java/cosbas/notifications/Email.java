package cosbas.notifications;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

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
     * The instance of the SimpleMailMessage that uses the template specified in the beans.xml document
     * This template is only for the email to the visitors
     */
    private static SimpleMailMessage visitorTemplateMessage;

    /**
     * The template method as specified in the Strategy Interface
     * Function that will send the email to the appropriate visitor
     * @param to - The email address of the visitor to which the email will be send to
     */
    @Override
    public void sendNotification(String to) {
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.STARTTLS.enable","true");
        emailProps.put("mail.smtp.auth","true");
        System.out.println("HERE1");

        SimpleMailMessage notification = new SimpleMailMessage(visitorTemplateMessage);
        notification.setTo(to);
        System.out.println("HERE2");

        //We can still add the necessary info here
        notification.setText(
                "Dear User <br>" +
                "An appointment has been scheduled by you."
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

    /**
     * Setter function to set the mailSender
     * @param mailSender - An instance of the mailSender
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Setter function to set the visitorTemplateMessage
     * @param visitorTemplateMessage - An instance of the visitorTemplateMessage
     */
    public void setVisitorTemplateMessage(SimpleMailMessage visitorTemplateMessage) {
        this.visitorTemplateMessage = visitorTemplateMessage;
    }
}
