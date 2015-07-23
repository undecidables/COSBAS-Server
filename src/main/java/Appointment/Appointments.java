package appointment;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Appointments class used to Request and cancel appointments. 
 * @author Elzahn Botha
 */

public class Appointments 
{

    /**
     * The database adapter/repository to use.
     */
    @Autowired
    private AppointmentDBAdapter repository;

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The repository to be injected.
     */
    public void setRepository(AppointmentDBAdapter repository) {
        this.repository = repository;
    }
    
    public String requestAppointment(List<String> visitorIDs, String staffID, LocalDateTime dateTime, String reason, int durationInMinutes){
        //check if time is available
        Appointment a = new Appointment(staffID, visitorIDs,dateTime, durationInMinutes, reason);

        repository.save(a);
        
        //save to calendar

        return a.getId();
    }
    
    public void cancelAppointment(String cancelleeID, String appointmentID){
        //find appointment with ID
        //check is cancelee is one who made the appointment/the appointment is with
        //if so change status
        //delete from calendar
        //Notify participants 
    }

    public void checkStatus(String appointmentID, String enquirer){
        //get appointment with ID
        //check is cancelee is one who made the appointment/the appointment is with
        //print information
    }

    public void approveAppointment(){
        //set appointment status
        //send confirmation email with the code
    }

    public void denyAppointment(){
        //set appointment status
        //remove from calendar
        //notify participants
    }
}