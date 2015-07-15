/**
 * @author Renette
 * MVC Controller for appointment System.
 * This conmtroller serves Thymeleaf HTML templates - so Mostly for GET Requests
 */

package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class AppointmentController {

  /**
  * Route function to go to index.html - Homepage for the user
  * @return index.html page
  */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Principal principal)
  {
    return principal == null ? "makeAppointment" : "index";
    //return "index";
  }

  /**
  * Route function to go to login.html - Login page for staffmembers to login on
  * @return login.html page
  */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login()
  {
    return "login";
  }

  /**
  * Route function to go to makeAppointment.html - Page for users to request an appointment with a staffmember
  * @return makeAppointment.html page
  */
  @RequestMapping(value = "/makeAppointment", method = RequestMethod.GET)
  public String makeAppointment()
  {
    return "makeAppointment";
  }

  /**
  * Route function to go to logout.html - confirmation page to ensure user wants to logout
  * @return logout.html page
  */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout()
  {
    return "logout";
  }

  /**
  * Route function to go to checkAppointment.html - Page where staff members can check all their appointments
  * @return checkAppointment.html page
  */
  @RequestMapping(value = "/checkAppointment", method = RequestMethod.GET)
  public String checkAppointment()
  {
    return "checkAppointment";
  }

  /**
  * Route function to go to addUser.html - Page where a new user can be added to the system
  * @return addUser.html page
  */
  @RequestMapping(value = "/addUser", method = RequestMethod.GET)
  public String addUser()
  {
    return "addUser";
  }

 /**
  * Route function to go to status.html - Page where a user can check the status of their appointments
  * @return status.html page
  */
  @RequestMapping(value = "/status", method = RequestMethod.GET)
  public String status()
  {
    return "status";
  }

   /**
  * Route function to go to cancel.html - Page where a user can cancel their appointments
  * @return cancel.html page
  */
  @RequestMapping(value = "/cancel", method = RequestMethod.GET)
  public String cancel()
  {
    return "cancel";
  }

 /**
  * Route function to go to approveDenyAppointment.html - Page where a staff member can approve or deny requested appointments
  * @return approveDenyAppointment.html page
  */
  @RequestMapping(value = "/approveDenyAppointment", method = RequestMethod.GET)
  public String approveDenyAppointment()
  {
    return "approveDenyAppointment";
  }


 /**
  * Route function to go to adderror.html - Page showing the user what error occured
  * @return error.html page
  */
  @RequestMapping(value = "/error", method = RequestMethod.GET)
  public String error()
  {
    return "error";
  }
}