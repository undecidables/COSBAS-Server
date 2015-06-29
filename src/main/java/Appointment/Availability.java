package Appointment;

import java.util.Date;

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

    public void setAvailability(Date begin, Date end){

    }

    public boolean chooseCalendar(String service, String username, String password){
    	return true;
    }

    public String getCalendar(){
    	return "empty string";
    }
}