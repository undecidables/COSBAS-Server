package cosbas.calendar_services.authorization;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * {@author Renette }
 */
public class GoogleVariables {
    static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email", CalendarScopes.CALENDAR);
    public static HttpTransport HTTP_TRANSPORT;
    public static final String APPLICATION_NAME = "COSBAS Calendar Integration Service";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**TODO: Value annotaion not working on static members. Make non-static and autowire somehow. */

    @Value("${google.clientID}")
    static String clientID = "914281078442-m8d9oi3bullig50oe13jufl0hc308lhf.apps.googleusercontent.com";
    @Value("${google.clientSecret}")
    static String clientSecret = "wtRGgiEgie96PdjWItD5OWp2";
    @Value("${google.redirectURI}")
    static String callbackURI = "http://localhost:8080/callback";


    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
}
