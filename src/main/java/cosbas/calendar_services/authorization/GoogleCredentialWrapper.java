package cosbas.calendar_services.authorization;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import cosbas.calendar_services.CalendarType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import java.io.IOException;

/**
 * @author Jason Richard Evans
 */
public class GoogleCredentialWrapper extends CredentialWrapper <Credential>{

    String refreshToken;
    String accessToken;

    @Value("${google.redirectURI}")
    private String callbackURI  = "http://localhost:8080/callback";

    @Transient
    private Credential credential;

    GoogleCredentialWrapper(String staffID, GoogleTokenResponse response) throws IOException {
        this(staffID, response.getRefreshToken(), response.getAccessToken());
        credential.refreshToken();
    }

    @PersistenceConstructor
    GoogleCredentialWrapper(String staffID, String refreshToken, String accessToken) {
        super(staffID, CalendarType.GOOGLE);
        this.refreshToken  = refreshToken;
        this.accessToken = accessToken;
        credential = new GoogleCredential.Builder()
                .setClientSecrets(GoogleVariables.clientID, GoogleVariables.clientSecret)
                .setJsonFactory(GoogleVariables.JSON_FACTORY).setTransport(GoogleVariables.HTTP_TRANSPORT).build()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
    }

    public String getAccessToken() throws IOException {
        credential.refreshToken();
        return credential.getAccessToken();
    }

    @Override
    public Credential getCredential(){
        try {
            credential.refreshToken();

            return credential;
        }
        catch (IOException error){
            error.printStackTrace();
        }

        return null;
    }
}
