package cosbas.calendar_services.authorization;

/**
 * {@author Jason Richard Evans}
 * <p>
 * Helper class for Google Authorization. Adapted from example available at {@link https://github.com/mdanter/OAuth2v1}
 */


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;

@Service
public class GoogleAuthorization implements Authorizer {

    private String stateToken;

    private GoogleAuthorizationCodeFlow codeFlow;


      @Override
      public void startFlow() {
          codeFlow = new GoogleAuthorizationCodeFlow.Builder(GoogleVariables.HTTP_TRANSPORT, GoogleVariables.JSON_FACTORY, GoogleVariables.getClientID(), GoogleVariables.getClientSecret(), GoogleVariables.SCOPE).setAccessType("offline").setApprovalPrompt("force").build();
          generateStateToken();
      }




    @Override
    public String buildLoginUrl() {
        final GoogleAuthorizationCodeRequestUrl url = codeFlow.newAuthorizationUrl();
        return url.setRedirectUri(GoogleVariables.callbackURI).setState(stateToken).build();
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
            final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(GoogleVariables.callbackURI).execute();
            return new GoogleCredentialWrapper(userID, resp);
        } catch (IOException error) {
            error.printStackTrace();
            return null;
        }
    }
}
