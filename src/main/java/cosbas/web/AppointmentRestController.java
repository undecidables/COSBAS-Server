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
   * @param appointmentWith - Staff memeber ID as gotten from the html dropdown on the html page
   * @param requestedDateTime - Requested date time for the appointment as inputted into the html form
   * @param appointmentBy - List of members in the group that is making the appointment as inputted on the htm form
   * @param appoitnmentDuration - Duration of the appointment in minutes as inputted on the html form
   * @param appointmentReason - Reason for the appointment being made as indicated on the html form
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
   * 
   * @param 
   * @param 
   * @return 
   */

  @RequestMapping(method= RequestMethod.POST, value="/cancelAppointment")
  public String cancelAppointment(
                     @RequestParam(value = "cancellee", required = true) String cancellee,
                     @RequestParam(value = "appointmentID", required = true) String appointmentID) {
    return appointment.cancelAppointment(cancellee, appointmentID);
  }
}
