package cosbas.notifications;


import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Vivian Venter on 10/4/2015.
 */
public class Email implements NotificationsStrategy  {

    private MailSender mailSender;
    private SimpleMailMessage visitorMessage;

    @Override
    public void sendNotification(String to) {
        SimpleMailMessage notification = new SimpleMailMessage(visitorMessage);
        notification.setTo(to);

        //We can still add the necessary info here
        notification.setText(
                "Dear User" +
                "An appointment has been scheduled by you."
        );

        try {
            mailSender.send(notification);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVisitorMessage(SimpleMailMessage visitorMessage) {
        this.visitorMessage = visitorMessage;
    }

}
