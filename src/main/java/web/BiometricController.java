/**
 * @author Renette
 * MVC COntroller for biometric system
 */

package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BiometricController {

  /**
   * An Example route, can be removed once we have something definite
   * @return greeting page
   */
  @RequestMapping(method= RequestMethod.GET, value="/biometrics/access")
  public String index(Model model) {
    model.addAttribute("success", false);


    //TODO figure out how to serve xml files with thymeleaf.  View resolver/template resolver/extensions .....
    return "access";
  }

}
