/**
 * @author Renette
 * MVC COntroller for biometric system
 * Responds with plain text to requests
 */

package web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BiometricController {

  @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/access")
  public String access(@RequestParam(value = "accessRequest", required = false, defaultValue = "World") String accessRequest, Model model) {

    //TODO Call access function and serialize response to JSON
    return "{TODO: Serialize response object to JSON}";
  }

}
