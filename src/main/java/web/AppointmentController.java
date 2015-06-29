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

@Controller
public class AppointmentController {

  /**
   * An Example route, can be removed once we have something definite
   * @param name
   * @return greeting page
   */
  @RequestMapping(method= RequestMethod.GET, value="/greeting")
  public String index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  //TODO: Map /  (root)
  //TODO: Map /error
}
