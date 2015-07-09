package Appointment;

import java.time.LocalDateTime;

//Google Calendar Imports
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

/**
 * Class to view and change the availability of a specific client.
 * @author Jason Evans
 */

public class Availability 
{
	private String myCalendar;

    public boolean isAvailable(){
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