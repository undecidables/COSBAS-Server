package cosbas.calendar_services;

/**
 * @author Jason Richard Evans
 */


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Arrays;

public class GoogleAuthorization {
    private static final String clientID = "211837377506-qrmd6oco0rsakeelsisd1r4gm8gc5a57.apps.googleusercontent.com";
    private static final String clientSecret = "TH6t1loSooacdV24ZD3bNhFk";
    private static final String callbackURI = "http://localhost:8080/"; /**TODO Change the return.**/

    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private String stateToken;

    private final GoogleAuthorizationCodeFlow codeFlow;
}
