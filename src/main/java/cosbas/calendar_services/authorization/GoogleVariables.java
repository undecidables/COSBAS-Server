package cosbas.calendar_services.authorization;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * {@author Renette }
 *
 * This class manages the Variables needed by various classes in the Google Calendar Workflow.
 */
@Component
public class GoogleVariables {
    static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email", CalendarScopes.CALENDAR);

    public static HttpTransport HTTP_TRANSPORT;
    public static final String APPLICATION_NAME = "COSBAS Calendar Integration Service";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static String callbackURI = "http://localhost:8080/callback";

    @Value("${google.clientID}")
    private String clientID;
    @Value("${google.clientSecret}")
    private String clientSecret;

    static String getClientID() {
        return instance.clientID;
    }

    static String getClientSecret() {
        return instance.clientSecret;
    }

    private static GoogleVariables instance = null;

    public static GoogleVariables getInstance() {
        return instance;
    }

    protected GoogleVariables() {
        if (instance == null) instance = this;
    }

    /*@PostConstruct
    private void test() {
        System.out.println();
    }*/

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }



}
