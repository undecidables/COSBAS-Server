package cosbas.calendar_services.authorization;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import cosbas.calendar_services.CalendarType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Jason Richard Evans
 */
public class GoogleCredentialWrapper extends CredentialWrapper {

    String refreshToken;

    private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**TODO: Value annotaion not working correctly because we are using new to instantiate this. Send it through the constructor or something */

    @Value("${google.clientID}")
    private static String clientID = "914281078442-m8d9oi3bullig50oe13jufl0hc308lhf.apps.googleusercontent.com";
    @Value("${google.clientSecret}")
    private static String clientSecret = "wtRGgiEgie96PdjWItD5OWp2";
    @Value("${google.redirectURI}")
    private String callbackURI  = "http://localhost:8080/callback";

    @Transient
    private static GoogleAuthorizationCodeFlow codeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientID, clientSecret, SCOPE).setAccessType("offline").setApprovalPrompt("force").build();

    @Transient
    private GoogleCredential credentials;

    GoogleCredentialWrapper(String staffID, GoogleTokenResponse response) {
        super(staffID, CalendarType.GOOGLE);
        this.refreshToken  = response.getRefreshToken();
        credentials = new GoogleCredential.Builder()
                .setClientSecrets(clientID, clientSecret)
                .setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).build()
                .setRefreshToken(refreshToken);
    }

    @PersistenceConstructor
    GoogleCredentialWrapper(String staffID, String refreshToken) {
        super(staffID, CalendarType.GOOGLE);
        this.refreshToken  = refreshToken;
        credentials = new GoogleCredential.Builder()
                .setClientSecrets(clientID, clientSecret)
                .setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).build()
                .setRefreshToken(refreshToken);
    }

    public String getAccessToken() throws IOException {
        credentials.refreshToken();
        return credentials.getAccessToken();
    }

    public GoogleCredential makeCredential(){
        credentials = new GoogleCredential.Builder()
                .setClientSecrets(clientID, clientSecret)
                .setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).build()
                .setRefreshToken(refreshToken);
        try {
            credentials.refreshToken();
            String authCode = credentials.getAccessToken();

            final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
            GoogleCredential cred = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setClientSecrets(clientID, clientSecret)
                    .build()
                    .setFromTokenResponse(resp);
            return cred;
        }
        catch (IOException error){
            error.printStackTrace();
        }

        return null;
    }
}
