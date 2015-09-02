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

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

public class GoogleAuthorization {
    private static final String clientID = "211837377506-qrmd6oco0rsakeelsisd1r4gm8gc5a57.apps.googleusercontent.com";
    private static final String clientSecret = "TH6t1loSooacdV24ZD3bNhFk";
    private static final String callbackURI = "http://localhost:8080/"; /**TODO Change the return.**/

    private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private String stateToken;

    private final GoogleAuthorizationCodeFlow codeFlow;

    public GoogleAuthorization(){
        codeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientID, clientSecret, SCOPE).build();
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

    public String getUserInfoJson(final String authCode) throws IOException{
        final GoogleTokenResponse resp = codeFlow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
        final Credential cred = codeFlow.createAndStoreCredential(resp, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(cred);
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String jsonIdentity = request.execute().parseAsString();

        return jsonIdentity;
    }
}
