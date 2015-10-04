package cosbas.notifications;

/**
 * Created by Vivian Venter on 10/4/2015.
 */
public class Notifications {
    NotificationsStrategy email = null;

    public void sendNotifications() {

        //Here we should get the email address and send all the appropriate info to be send
        email.sendNotification("u13238435@tuks.co.za");
    }
}
