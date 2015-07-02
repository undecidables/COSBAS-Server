/**
 * @author Renette
 * Secondary controller for appointment System.
 * This controller serves plain text. It is used for any URL'S that respond to Ajax requests with JSON.
 */

package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentRestController {

  /**
   * An Example route, can be removed once we have something definite
   * @param name Name to greet
   * @return greeting JSON object
   */
  @RequestMapping(method= RequestMethod.POST, value="/greeting")
  public String index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
    return "{greeting: \"Hello " + name + "\"}";
  }

}