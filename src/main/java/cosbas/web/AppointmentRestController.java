/**
 * @author Renette
 * Secondary controller for appointment System.
 * This controller serves plain text. It is used for any URL'S that respond to Ajax requests with JSON.
 */

package cosbas.web;

import cosbas.appointment.Appointments;
import cosbas.appointment.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentRestController {

  @Autowired
  public AppointmentRestController(Appointments appointments, Availability availability) {
    this.appointments = appointments;
    this.availability = availability;
  }

  private final Appointments appointments;
  private final Availability availability;
  /**
   * 
   * @param 
   * @return 
   */
  @RequestMapping(method= RequestMethod.POST, value="/requestAppointment")
  public String requestAppointment(@RequestParam(value = "appointmentWith", required = true) String appointmentWith,
  								   @RequestParam(value = "requestedDateTime", required = true) String appointmentDateTime,
  								   @RequestParam(value = "appointmentBy", required = true) String appointmentBy) {
  	System.out.println(appointmentWith + " " + appointmentDateTime + " " + appointmentBy);
    return "{greeting: \"Hello " + appointmentWith + "\"}";
  }

}
