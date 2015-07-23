package appointment;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Appointments class used to Request and cancel appointments. 
 * @author Elzahn Botha
 */


//Not sure if any of this works yet still working on it



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
        Appointment tempAppointment = repository.findById(appointmentID);
        //check is cancellee is one who made the appointment/the appointment is with
         List<String> tempVisitors = tempAppointment.getVisitorIDs();
         foreach(String visitor in tempVisitors)
         {
            //if so change status
            if(visitor == cancelleeID)
            {
                tempAppointment.setStatus("Cancelled");
                //delete from calendar
                //Notify participants 
                return;
            }
         }        
    }

    /**
     * Function used to check ont he status of a requested/made apointment
     * @param appointmentID - The appointmen's unique identifier 
     * @param enquirer - The identifier of the person enquiring about the appointment's status. Must be a participant of the appointment
     */
    public void checkStatus(String appointmentID, String enquirer){
        //get appointment with ID
         Appointment tempAppointment = repository.findById(appointmentID);
        //check is enquirer is one who made the appointment/the appointment is with
          List<String> tempVisitors = tempAppointment.getVisitorIDs();
         foreach(String visitor in tempVisitors)
         {
            //if so
            if(visitor == enquirer)
            {
                //print information/send back not sure yet
                return;
            }
         } 
        
    }

    /**
     * Function used by staff members to approve a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public void approveAppointment(String appointmentID){
        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);
        //set appointment status
        tempAppointment.setStatus("Approved");
        //send confirmation email with the code
    }

    /**
     * Function used by staff members to deny a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public void denyAppointment(String appointmentID){
        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);
        //set appointment status
         tempAppointment.setStatus("Denied");
        //remove from calendar
        //notify participants
    }
}