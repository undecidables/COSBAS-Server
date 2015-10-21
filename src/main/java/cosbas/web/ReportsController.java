package cosbas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  {@author Szymon}
 */

@Controller
public class ReportsController {

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String reports()
    {
        return "reports";
    }
}
