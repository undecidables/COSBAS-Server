package cosbas.appointment;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.*;
import cosbas.calendar_services.services.GoogleCalendarService;
import cosbas.notifications.Email;
import cosbas.notifications.Notifications;
import cosbas.notifications.Notify;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Appointments class used to Request, cancel, check the status, accept/deny appointments. 
 * @author Elzahn Botha
 */
@Service
public class Appointments 
{
    Notifications notifyEmail;

    /**
     * The database adapter/credentialRepository to use.
     */
    @Autowired
    private AppointmentDBAdapter repository;

    @Autowired
    private GoogleCalendarService calendar;

    @Autowired
    private AccessCodeGenerator visitorCodes;

    @Autowired
    private BiometricDataDAO codeRepository;

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The credentialRepository to be injected.
     */
    public void setRepository(AppointmentDBAdapter repository) {
        this.repository = repository;
    }

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The AccessCodeRepository to be injected.
     */
    public void setRepository(BiometricDataDAO repository) {
        this.codeRepository = repository;
    }
    
     /**
     * Function used to request an appointment with a staff member using the system
     * @param visitorIDs - The identified of who the visitor is that wants to make an appointment
     * @param staffID - The identifier of who the appointment is with, the staff member
     * @param dateTime - The date and time of the requested appointment
     * @param reason - The reason for the requested appointment
     * @param durationInMinutes - How long you would like the appointment to be
     * @return String appointmentID - The appointment's unique identifier
     */
     @Notify
    public String requestAppointment(List<String> visitorIDs, String staffID, LocalDateTime dateTime, String reason, int durationInMinutes, List<String> emails){
        //check is staff member exists
        if(calendar.isAvailable(staffID, dateTime, durationInMinutes)){
			String a = calendar.makeAppointment(staffID, dateTime, durationInMinutes, reason, visitorIDs, emails);
            String[] tempString = a.split(" ");

            return "Appointment "+ tempString[0] + " has been saved.";
        } else {
            
            return "Time not available";
        }
		        
    }
    
     /**
     * Function used to cancel an appointment once it has been made/requested
     * @param cancelleeID - Identifier of the person wanting to cancel the appointment. Must be a participant of the appointment
     * @param appointmentID - The appointment's unique identifier 
     */
     @Notify
    public String cancelAppointment(String cancelleeID, String appointmentID){        
        //find appointment with ID
        Appointment tempAppointment = repository.findById(appointmentID);

        if(tempAppointment != null) {

            //check is cancellee is one who made the appointment/the appointment is with
             List<String> tempVisitors = tempAppointment.getVisitorIDs();

            //Notifications
            notifyEmail = new Notifications();
            notifyEmail.setEmail(new Email());

            ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
            ContactDetail contactDetailsStaff = null;

            //create ContactDetail objects for visitors
            for(String s: tempVisitors) {
                if (s.equals(tempVisitors.get(tempVisitors.size()-1))) {
                    contactDetailsStaff = new ContactDetail(ContactTypes.EMAIL,s);
                }
                else {
                    contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                }
            }

             for(Iterator<String> i = tempVisitors.iterator(); i.hasNext();)
             {
                String visitor = i.next();

                if(visitor.equals(cancelleeID) && !tempAppointment.getStatus().equals("Cancelled"))
                {            
                    calendar.removeAppointment(tempAppointment.getStaffID(), appointmentID);
                    //revoke access of key

                    notifyEmail.sendNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.CANCEL_APPOINTMENT, null, tempAppointment, false);
                    return "Appointment has been cancelled.";
                } else if (tempAppointment.getStatus().equals("Cancelled"))
                {
                    return "Appointment has already been cancelled";
                }
             }  

             //check if cancelleeID is with whom the appointment is with
             if(cancelleeID.equals(tempAppointment.getStaffID()) && !tempAppointment.getStatus().equals("Cancelled"))
             {
                    calendar.removeAppointment(tempAppointment.getStaffID(), appointmentID);
                    notifyEmail.sendNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.CANCEL_APPOINTMENT, null, tempAppointment, true);
                    return "Appointment has been cancelled.";
             } else if (tempAppointment.getStatus().equals("Cancelled"))
             {
                return "Appointment has already been cancelled.";
             }

             return "You are not authorised to cancel this appointment";
        }     
         return "Appointment does not exist.";
    }

    /**
     * Function used to check on the status of a requested/made appointment
     * @param appointmentID - The appointment's unique identifier
     * @param enquirer - The identifier of the person enquiring about the appointment's status. Must be a participant of the appointment
     */
    public String checkStatus(String enquirer, String appointmentID){
        //get appointment with ID
         Appointment tempAppointment = repository.findById(appointmentID);

         if(tempAppointment != null) {
            //check is enquirer is one who made the appointment/the appointment is with
            List<String> tempVisitors = tempAppointment.getVisitorIDs();
             for(Iterator<String> i = tempVisitors.iterator(); i.hasNext();)
             {
                String visitor = i.next();
                if(visitor.equals(enquirer))
                {
                    String[] parts = tempAppointment.getDateTime().toString().split("T");
                    String tempDateTime = parts[0] + " at " + parts[1].substring(0, parts[1].length()-3);

                    return "Appointment: " + tempAppointment.getId() + "\nWith: " + tempAppointment.getStaffID() + "\nOn: " + tempDateTime + "\nStatus: " + tempAppointment.getStatus();
                }
             } 

             //check if enquirer is with whom the appointment is with
             if(enquirer.equals(tempAppointment.getStaffID()))
             {
                String[] parts = tempAppointment.getDateTime().toString().split("T");
                String tempDateTime = parts[0] + " at " + parts[1];

                return "Appointment: " + tempAppointment.getId() + "\nWith: " + tempAppointment.getStaffID() + "\nOn: " + tempDateTime + "\nStatus: " + tempAppointment.getStatus();
             }
            return "You are not authorised to view this appointment";
         }
        return "No such Appointment exists";
    }

    /**
     * Function used by staff members to approve a requested appointment
     * @param appointmentID - The appointment's unique identifier
     */
    @Notify
    public String approveAppointment(String appointmentID, String staffID){
        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);

        //check if status is requested and that person is authorised to approve appointment
        if(tempAppointment != null && tempAppointment.getStatus().equals("requested") && staffID.equals(tempAppointment.getStaffID()))
        {

            tempAppointment.setStatus("Approved");
            repository.save(tempAppointment);

            TemporaryAccessCode code;
            List<TemporaryAccessCode> generatedCodes = visitorCodes.getTemporaryAccessCode(tempAppointment);

            int i = 0;
            for (TemporaryAccessCode a: generatedCodes) {
                String visitor = tempAppointment.getVisitorIDs().get(i);
                code = new TemporaryAccessCode(tempAppointment.getId(),visitor,a.getData(),a.getValidFrom(),a.getValidTo());
                codeRepository.save(code);
                i++;
            }

            return "Appointment approved";
        } else if(tempAppointment == null){
            return "No such Appointment exists";
        } else if(!staffID.equals(tempAppointment.getStaffID())) {
             return "You are not authorised to accept this appointment";
        } else {
             return "Appointment was already " + tempAppointment.getStatus();
        }
    }

    /**
     * Function used by staff members to deny a requested appointment
     * @param appointmentID - The appointment's unique identifier
     */
    public String denyAppointment(String appointmentID, String staffID){

        //find the appointment in the db
        Appointment tempAppointment = repository.findById(appointmentID);

        //check if status is requested
        if(tempAppointment != null && tempAppointment.getStatus().equals("requested")  && staffID.equals(tempAppointment.getStaffID()))
        {
            calendar.removeAppointment(tempAppointment.getStaffID(), appointmentID);

            //Notifications
            notifyEmail = new Notifications();
            notifyEmail.setEmail(new Email());

            List<String> attendants = tempAppointment.getVisitorIDs();

            ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
            ContactDetail contactDetailsStaff = null;

            //create ContactDetail objects for visitors
            for(String s: attendants) {
                if (s.equals(attendants.get(attendants.size()-1))) {
                    contactDetailsStaff = new ContactDetail(ContactTypes.EMAIL,s);
                }
                else {
                    contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                }
            }

            notifyEmail.sendNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.DENY_APPOINTMENT, null, tempAppointment, false);

            return "Appointment denied";
        } else if(tempAppointment == null){
            return "No such Appointment exists";
        } else if(!staffID.equals(tempAppointment.getStaffID())) {
            return "You are not authorised to deny this appointment";
        } else {
             return "Appointment was already " + tempAppointment.getStatus();
        }
    }
}