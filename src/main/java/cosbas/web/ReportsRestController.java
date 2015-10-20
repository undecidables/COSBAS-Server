package cosbas.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *  {@author Szymon}
 */

@RestController
public class ReportsRestController {


    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsReport")
    public String createAllAppointmentsReport() {
        return "yay";
    }
}
