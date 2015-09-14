package cosbas.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Iterator;
import java.lang.System;
import java.io.*;

/**
 * Appointments class used to Request, cancel, check the status, accept/deny appointments. 
 * @author Elzahn Botha
 */
@Service
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
        //check is staffmemeber exists
        //check if time is available - if not send error with suggested time
        Appointment a = new Appointment(staffID, visitorIDs, dateTime, durationInMinutes, reason);

        repository.save(a);

        //save to calendar
        //notify staff memeber
        return "Appointment " + a.getId() + " has been saved.";

        //else throws Exception/return error message string
    }
    
     /**
     * Function used to cancel an appointment once it has been made/requested
     * @param cancelleeID - Identifier of the person wanting to cancel the appointment. Must be a participant of the appointment
     * @param appointmentID - The appointmen's unique identifier 
     */
    public String cancelAppointment(String cancelleeID, String appointmentID){        
        //find appointment with ID
       
        Appointment tempAppointment = repository.findById(appointmentID);

        if(tempAppointment != null) {
            //check is cancellee is one who made the appointment/the appointment is with
             List<String> tempVisitors = tempAppointment.getVisitorIDs();

             for(Iterator<String> i = tempVisitors.iterator(); i.hasNext();)
             {
                String visitor = i.next();
                //if so change status

                if(visitor.equals(cancelleeID) && !tempAppointment.getStatus().equals("Cancelled"))
                {            
                    tempAppointment.setStatus("Cancelled");
                    repository.save(tempAppointment);

                    //delete from calendar
                    //Notify participants 
                    //revoke access
                    return "Appointment " + tempAppointment.getId() + " has been cancelled.";
                } else if (tempAppointment.getStatus().equals("Cancelled"))
                {
                    //throw an excetion
                    return "Appointment " + tempAppointment.getId() + " has already been cancelled.";
                }
             }  

             //check if cancelleeID is with whom the appointment is with
             if(cancelleeID.equals(tempAppointment.getStaffID()) && !tempAppointment.getStatus().equals("Cancelled"))
             {
                 tempAppointment.setStatus("Cancelled");
                    repository.save(tempAppointment);

                    //delete from calendar
                    //Notify participants 
                    return "Appointment " + tempAppointment.getId() + " has been cancelled.";
             } else if (tempAppointment.getStatus().equals("Cancelled"))
             {
                //throw an excetion
                return "Appointment " + tempAppointment.getId() + " has already been cancelled.";
             }

             return "You are not authorised to cancel appointment " + tempAppointment.getId();
             //else throws Exception not authorised
        }     
             //else throws Exception Appointment doesn't exist
         return "Appointment " + appointmentID + " does not exist.";
    }

    /**
     * Function used to check on the status of a requested/made apointment
     * @param appointmentID - The appointmen's unique identifier 
     * @param enquirer - The identifier of the person enquiring about the appointment's status. Must be a participant of the appointment
     */
    //Pre - appointment must exist
    //Pre - enquirer must be envolved with the appointment
    //Post - Data is displayed/returned
    public String checkStatus(String enquirer, String appointmentID){
        //get appointment with ID
         Appointment tempAppointment = repository.findById(appointmentID);

         if(tempAppointment != null) {
            //check is enquirer is one who made the appointment/the appointment is with
            List<String> tempVisitors = tempAppointment.getVisitorIDs();
             for(Iterator<String> i = tempVisitors.iterator(); i.hasNext();)
             {
                String visitor = i.next();
                //if so
                if(visitor.equals(enquirer))
                {
                    String[] parts = tempAppointment.getDateTime().toString().split("T");
                    String tempDateTime = parts[0] + " at " + parts[1];

                    return "Appointment " + tempAppointment.getId() + " with " + tempAppointment.getStaffID() + " is on " + tempDateTime + " and is: " + tempAppointment.getStatus();
                    //print information/send back not sure yet
                    //return;
                }
             } 

             //check if enquirer is with whom the appointment is with
             if(enquirer.equals(tempAppointment.getStaffID()))
             {
                String[] parts = tempAppointment.getDateTime().toString().split("T");
                String tempDateTime = parts[0] + " at " + parts[1];

                return "Appointment " + tempAppointment.getId() + " with " + tempAppointment.getStaffID() + " is on " + tempDateTime + " and is: " + tempAppointment.getStatus();
                //print information/send back not sure yet
                //return;
             }
            //else throws Exception
            return "You are not authorised to view this appointment";
         }
        return "No such Appointment exists";
    }

    /**
     * Function used by staff members to approve a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public String approveAppointment(String appointmentID, String staffID){
        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);
        
        //check if status is requested and that perspon is authorised to approve appointment
        if(tempAppointment != null && tempAppointment.getStatus().equals("requested") && staffID.equals(tempAppointment.getStaffID()))
        {
            //set appointment status
            tempAppointment.setStatus("Approved");
            repository.save(tempAppointment);
            //send confirmation email with the code
            return "Appointment approved";
        } else if(tempAppointment == null){
            //throw exception
            return "No such Appointment exists";
        } else if(!staffID.equals(tempAppointment.getStaffID())) {
             //throw exception
             return "You are not authorised to accept this appointment";
        } else {
            //throw exception
             return "Appointment was already " + tempAppointment.getStatus();
        }
    }

    /**
     * Function used by staff members to deny a requested appointment
     * @param appointmentID - The appointmen's unique identifier
     */
    public String denyAppointment(String appointmentID, String staffID){
        //check is perspon is authorised to approve appointment

        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);

        //check if status is requested
        if(tempAppointment != null && tempAppointment.getStatus().equals("requested")  && staffID.equals(tempAppointment.getStaffID()))
        {
            //set appointment status
            tempAppointment.setStatus("Denied");
            repository.save(tempAppointment);
            //remove from calendar
            //notify participants
            return "Appointment denied";
        } else if(tempAppointment == null){
            //throw exception
            return "No such Appointment exists";
        } else if(!staffID.equals(tempAppointment.getStaffID())) {
             //throw exception
            return "You are not authorised to cancel this appointment";
        } else {
            //throw exception
             return "Appointment was already " + tempAppointment.getStatus();
        }
    }
}