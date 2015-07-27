/**
 * @author Renette
 * MVC Controller for appointment System.
 * This conmtroller serves Thymeleaf HTML templates - so Mostly for GET Requests
 */

package cosbas.web;

import cosbas.appointment.Appointments;
import cosbas.appointment.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentController {

  @Autowired
  public AppointmentController(Appointments appointments, Availability availability) {
    this.appointments = appointments;
    this.availability = availability;
  }

  private final Appointments appointments;
  private final Availability availability;

  /**
   * An Example route, can be removed once we have something definite
   * @param name
   * @return greeting page
   */
  @RequestMapping(method= RequestMethod.GET, value="/greeting")
  public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  /**
  * Route function to go to index.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return index.html page
  */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "index";
  }

  /**
  * Route function to go to login.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return login.html page
  */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "login";
  }

  /**
  * Route function to go to makeAppointment.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return makeAppointment.html page
  */
  @RequestMapping(value = "/makeAppointment", method = RequestMethod.GET)
  public String makeAppointment(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "makeAppointment";
  }

  /**
  * Route function to go to logout.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return logout.html page
  */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "logout";
  }

  /**
  * Route function to go to checkAppointment.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return checkAppointment.html page
  */
  @RequestMapping(value = "/checkAppointment", method = RequestMethod.GET)
  public String checkAppointment(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "checkAppointment";
  }

  /**
  * Route function to go to addUser.html
  * @param name - used to determin which menu to use. If anything is stored in name the logged in menu is used. 
  * @return addUser.html page
  */
  @RequestMapping(value = "/addUser", method = RequestMethod.GET)
  public String addUser(@RequestParam(value = "name", required = false, defaultValue = "") String name, Model model)
  {
    model.addAttribute("name", name);
    return "addUser";
  }

  //TODO: Map /  (root)
  //TODO: Map /error
}