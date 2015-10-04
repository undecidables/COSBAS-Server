/**
 * @author Renette
 * Secondary controller for appointment System.
 * This controller serves plain text. It is used for any URL'S that respond to Ajax requests with JSON.
 */

package cosbas.web;

import org.springframework.beans.factory.annotation.Autowired;
import cosbas.appointment.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.LocalDateTime;
import com.google.common.base.Joiner;

@RestController
public class AppointmentRestController {
    
    //Private variable used to call the appointment class functions of the appointment class
    @Autowired
    private Appointments appointment;

    //The database adapter/repository to use.
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
   * Fuction used to save the appointment that the user has inputted into the html form on the makeAppointment.html page
   * @param appointmentWith - String staff memeber ID as gotten from the html dropdown on the html page
   * @param appointmentDateTime - String Requested date time for the appointment as inputted into the html form it is converted to LocalDateTime in the function
   * @param appointmentBy - String list of members in the group that is making the appointment as inputted on the htm form
   * @param duration - Integer duration of the appointment in minutes as inputted on the html form
   * @param reason - String reason for the appointment being made as indicated on the html form
   * @return the returned string from the requestAppointment function - It can either be an error message or the appointment identifier
   */

  @RequestMapping(method= RequestMethod.POST, value="/requestAppointment")
  public String requestAppointment(
                     @RequestParam(value = "appointmentWith", required = true) String appointmentWith,
                     @RequestParam(value = "requestedDateTime", required = true) String appointmentDateTime,
                     @RequestParam(value = "appointmentBy", required = true) List<String> appointmentBy,
                     @RequestParam(value = "appointmentDuration", required = true) int duration,
                     @RequestParam(value = "appointmentReason", required = true) String reason) {

    LocalDateTime dateTime = LocalDateTime.parse(appointmentDateTime);
    return appointment.requestAppointment(appointmentBy, appointmentWith, dateTime, reason, duration);
  }

  /**
   * Function used to cancel an appointment via the form on the cancel.html page
   * @param cancellee - String of the name of the person who wants to cancel the appointment. 
   * @param appointmentID - String appointmentID, the appointment ID of the appointment that is being cancelled.
   * @return the status of the appointment - whether the appoitnment was canceled or if an error occured
   */

  @RequestMapping(method= RequestMethod.POST, value="/cancelAppointment")
  public String cancelAppointment(
                     @RequestParam(value = "cancellee", required = true) String cancellee,
                     @RequestParam(value = "appointmentID", required = true) String appointmentID) {

    return appointment.cancelAppointment(cancellee, appointmentID);
  }

  /**
   * Function to check the status of the appointment entered on the status.html form
   * @param requester - String name of the person requesting the status of the appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to check
   * @return Returns the status and information of the appointment or an appropriate string describing the error
   */

  @RequestMapping(method= RequestMethod.POST, value="/status")
  public String checkStatus(
                     @RequestParam(value = "requester", required = true) String requester,
                     @RequestParam(value = "appointmentID", required = true) String appointmentID) {

    return appointment.checkStatus(requester, appointmentID);
  }

  /**
   * Function used to set up the requested appointments that need to be approved or denied. It is called as soon as the page is loaded
   * @return Returns the approve or deny options of each relevant appointment
   */

  @RequestMapping(method= RequestMethod.POST, value="/getApproveOrDeny")
  public String getApproveOrDeny() {
    List<Appointment> appointments = repository.findByStatusLike("requested");
    String staffMember = "Staff member";
    String returnPage = "";

    for(int i = 0; i < appointments.size(); i++)
    {
      String[] parts = appointments.get(i).getDateTime().toString().split("T");
      String tempDateTime = parts[0] + " at " + parts[1];

      if(appointments.get(i).getStaffID().equals(staffMember)){
        returnPage += "<div class='form-group'><p class='text-left'>Appointment with " + Joiner.on(", ").join(appointments.get(i).getVisitorIDs()) + " on " + tempDateTime + " for " + appointments.get(i).getDurationMinutes() + " minutes</p><input class='form-control accept' type='submit' value='Approve'/><input class='form-control deny' type='submit' value='Deny'/>";
        returnPage += "<input type='text' class='appointmentID' value='" + appointments.get(i).getId() + "' hidden/><input type='text' class='staffID' value='" + staffMember + "' hidden/>";
        returnPage += "</div>";
      }
    }
      
    if(appointments.size() == 0){
      returnPage += "<p>No appointments pending</p>";
    }

    return returnPage;
  }

  /**
   * Function to approve a pending appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to approve
   * @param staffMember - StaffID of the staff member who is approving the appointment. Used to check that it is your appointmnet that you are approving
   * @return Returns the message detailing what happened when trying to approve the appointment as gotten from the approveAppointment method
   */
  @RequestMapping(method= RequestMethod.POST, value="/approve")
   public String approve( @RequestParam(value = "appointmentID", required = true) String appointmentID,
                          @RequestParam(value = "staffMember", required = true) String staffMember) {

      return appointment.approveAppointment(appointmentID, staffMember);
   }

   /**
   * Function to deny a pending appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to deny
   * @param staffMember - StaffID of the staff member who is denying the appointment. Used to check that it is your appointmnet that you are denying
   * @return Returns the message detailing what happened when trying to deny the appointment as gotten from the denyAppointment method
   */
  @RequestMapping(method= RequestMethod.POST, value="/deny")
   public String deny( @RequestParam(value = "appointmentID", required = true) String appointmentID,
                          @RequestParam(value = "staffMember", required = true) String staffMember) {

      return appointment.denyAppointment(appointmentID, staffMember);
   }
}
