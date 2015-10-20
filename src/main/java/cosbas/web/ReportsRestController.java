package cosbas.web;

import cosbas.reporting.ReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *  {@author Szymon}
 */

@RestController
public class ReportsRestController {

    @Autowired
    ReportFactory reports;

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsReport")
    public byte[] createAllAppointmentsReport() {


        return "yay".getBytes();
    }
}
