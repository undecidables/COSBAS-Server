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
    
     /**
     * Function used to request an appointment with a staff member using the system
     * @param visitorID - The identified of who the visitor is that wants to make an appointment
     * @param staffID - The identifier of who the appointment is with, the staff member
     * @param dateTime - The date and time of the requested apointment
     * @param reason - The reson for the requested appointment
     * @param durationInMinutes - How long you would like the appointment to be
     * @return String appointmentID - The appointment's unique identifier
     */
    public String requestAppointment(List<String> visitorIDs, String staffID, LocalDateTime dateTime, String reason, int durationInMinutes){
        //check if time is available
        Appointment a = new Appointment(staffID, visitorIDs,dateTime, durationInMinutes, reason);

        repository.save(a);

        //save to calendar

        return a.getId();
    }
    
     /**
     * Function used to cancel an appointment once it has been made/requested
     * @param cancelleeID - Identifier of the person wanting to cancel the appointment. Must be a participant of the appointment
     * @param appointmentID - The appointmen's unique identifier 
     */
    public void cancelAppointment(String cancelleeID, String appointmentID){
        //find appointment with ID
        //check is cancelee is one who made the appointment/the appointment is with
        //if so change status
        //delete from calendar
        //Notify participants 
    }

    /**
     * Function used to check ont he status of a requested/made apointment
     * @param appointmentID - The appointmen's unique identifier 
     * @param enquirer - The identifier of the person enquiring about the appointment's status. Must be a participant of the appointment
     */
    public void checkStatus(String appointmentID, String enquirer){
        //get appointment with ID
        //check is enquirer is one who made the appointment/the appointment is with
        //print information/send back not sure yet
    }

    /**
     * Function used by staff members to approve a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public void approveAppointment(String appointmentID){
        //set appointment status
        //send confirmation email with the code
    }

    /**
     * Function used by staff members to deny a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public void denyAppointment(String appointmentID){
        //set appointment status
        //remove from calendar
        //notify participants
    }
}