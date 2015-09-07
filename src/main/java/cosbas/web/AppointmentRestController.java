/**
 * @author Renette
 * Secondary controller for appointment System.
 * This controller serves plain text. It is used for any URL'S that respond to Ajax requests with JSON.
 */

package cosbas.web;

import org.springframework.beans.factory.annotation.Autowired;
import cosbas.appointment.Appointments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.LocalDateTime;

@RestController
public class AppointmentRestController {
    
    //Private variable used to call the appointment class fuctions of the appointment class
    @Autowired
    private Appointments appointment;

  /**
   * Fuction used to save the appointment that the user has inputted into the html form on the makeAppointment.html page
   * @param appointmentWith - String staff memeber ID as gotten from the html dropdown on the html page
   * @param requestedDateTime - String Requested date time for the appointment as inputted into the html form it is converted to LocalDateTime in the function
   * @param appointmentBy - String list of members in the group that is making the appointment as inputted on the htm form
   * @param appoitnmentDuration - Integer duration of the appointment in minutes as inputted on the html form
   * @param appointmentReason - String reason for the appointment being made as indicated on the html form
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
   * @param appoitnmentID - String appointmentID, the appointment ID of the appointment that is being cancelled. 
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
   * Login to the COSBAS system
   * @param username - String username of the person trying to login
   * @param password - String password connected to the username entered
   * @return A message saying that you could not be logged in - only shown if authentication failed
   */

  @RequestMapping(method= RequestMethod.POST, value="/login")
  public String login(
                     @RequestParam(value = "username", required = true) String username,
                     @RequestParam(value = "password", required = true) String password) {
    return appointment.checkStatus(username, password);
  }

}
