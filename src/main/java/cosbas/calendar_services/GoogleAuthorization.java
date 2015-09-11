package cosbas.calendar_services;

/**
 * @author Jason Richard Evans
 */


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

public class GoogleAuthorization {
    private static final String clientID = "914281078442-m8d9oi3bullig50oe13jufl0hc308lhf.apps.googleusercontent.com";
    private static final String clientSecret = "wtRGgiEgie96PdjWItD5OWp2";
    private static final String callbackURI = "http://localhost:8080/callback"; /**TODO Change the return.**/

    private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private String stateToken;
    private Credential cred;

    private final GoogleAuthorizationCodeFlow codeFlow;

    @Autowired
    private CalendarDBAdapter repository;

    public void setRepository(CalendarDBAdapter repo){
        this.repository = repo;
    }

    public GoogleAuthorization(){
        codeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientID, clientSecret, SCOPE).setAccessType("offline").setApprovalPrompt("force").build();
        generateStateToken();
    }

    public String buildLoginUrl(){
        final GoogleAuthorizationCodeRequestUrl url = codeFlow.newAuthorizationUrl();
        return url.setRedirectUri(callbackURI).setState(stateToken).build();
    }

    private void generateStateToken(){
        SecureRandom srGen = new SecureRandom();
        stateToken = "google;" + srGen.nextInt();
    }

    public String getStateToken(){
        return stateToken;
    }

    public boolean storeCredential(final String emplid, final String authCode){
        try {
            final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
            System.out.println(resp.toPrettyString());
            cred = codeFlow.createAndStoreCredential(resp, null);
            CredentialWrapper obj = new GCredWrapper(emplid, cred, CalendarType.GOOGLE);
            repository.save(obj);
            System.out.println("Stored Credential Object");
            return true;
        }
        catch (IOException error){
            error.printStackTrace();
            return false;
        }
    }

    public String getUserInfoJson(final String authCode) throws IOException{
        final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
        cred = codeFlow.createAndStoreCredential(resp, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(cred);
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String jsonIdentity = request.execute().parseAsString();

        return jsonIdentity;
    }
}
