package cosbas.calendar_services.authorization;

/**
 * {@author Jason Richard Evans}
 * <p>
 * Helper class for Google Authorization. Adapted from example available at {@link https://github.com/mdanter/OAuth2v1}
 */


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

@Service
public class GoogleAuthorization implements Authorizer {
    private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Value("${google.clientID}")
    private String clientID;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Value("${google.redirectURI}")
    private String callbackURI;
    private String stateToken;

    private GoogleAuthorizationCodeFlow codeFlow;


      @Override
      public void startFlow() {
          codeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientID, clientSecret, SCOPE).setAccessType("offline").setApprovalPrompt("force").build();
          generateStateToken();
      }




    @Override
    public String buildLoginUrl() {
        final GoogleAuthorizationCodeRequestUrl url = codeFlow.newAuthorizationUrl();
        return url.setRedirectUri(callbackURI).setState(stateToken).build();
    }

    private void generateStateToken() {
        SecureRandom srGen = new SecureRandom();
        stateToken = "google;" + srGen.nextInt();
    }

    public String getStateToken() {
        return stateToken;
    }

    @Override
    public CredentialWrapper getCredential(String userID, final String authCode) {
        try {
            final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
            return new GoogleCredentialWrapper(userID, resp);
        } catch (IOException error) {
            error.printStackTrace();
            return null;
        }
    }
}
