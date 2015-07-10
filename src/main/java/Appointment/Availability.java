package Appointment;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//Google Calendar and OAuth Imports
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

/**
 * Class to view and change the availability of a specific client.
 * @author Jason Evans
 */

public class Availability 
{
    private static final String APPLICATION_NAME = "COSBAS Calendar";

    /**
     * TODO Storing the Credentials of a user after authentication...
     */

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR); //This gives us read write access to the calendar.
    static{
        try{
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            /**
             * TODO creating the data store factory to store credentails
             */
        }
        catch(Throwable error){
            error.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorizeCOSBAS() throws IOException{
        InputStream in = Availability.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        //building the flow and trigger for user authorization request...
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Please note that credentials have not yet been persisted. This means that we will have to authenticate with each use.");
        return credential;
        /**
         * TODO
         * This is the credential object that we should persist in the database...
         */
    }

    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException{
        Credential credential = authorizeCOSBAS();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public boolean isAvailable() throws IOException{
        com.google.api.services.calendar.Calendar service = getCalendarService();
    	return true;
    }

    public void setAvailability(LocalDateTime begin, LocalDateTime end){

    }

    public boolean chooseCalendar(String service, String username, String password){
    	return true;
    }

    public String getCalendar(){
    	return "empty string";
    }
}