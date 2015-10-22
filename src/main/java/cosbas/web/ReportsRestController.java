package cosbas.web;

import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.CredentialWrapper;
import cosbas.logging.AuthenticateReports;
import cosbas.reporting.ReportData;
import cosbas.reporting.ReportFactory;
import cosbas.reporting.ReportFormatter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *  {@author Szymon}
 */

@RestController
public class ReportsRestController {

    @Autowired
    public ReportsRestController(ReportFactory reports)
    {
        this.reports = reports;
    }
    ReportFactory reports;



    @RequestMapping(value = "/downloads/reports/{file_name}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName,
                        HttpServletResponse response) {
        try {

            Path path = Paths.get("downloads/reports/" + fileName);

            InputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            File file = path.toFile();
            if(file.exists())
            {
                file.delete();
            }
        } catch (IOException ex) {
        }

    }



    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsReport")
    public String createAllAppointmentsReport(Principal principal,
                                              @RequestParam(value = "format", required = true) String format, HttpServletResponse response) {
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS, new ReportData(), ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdReports")
    public String createAllAppointmentsByStaffIdReports(Principal principal,
                                                        @RequestParam(value = "format", required = true) String format,
                                                        @RequestParam(value = "staffID", required = true) String staffID) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStatusReports")
    public String createAllAppointmentsByStatusReports(Principal principal,
                                                       @RequestParam(value = "format", required = true) String format,
                                                       @RequestParam(value = "status", required = true) String status) {
        ReportData data = new ReportData();
        data.setStatus(status);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STATUS, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsBetweenDateTimeReports")
    public String createAllAppointmentsBetweenDateTimeReports(Principal principal,
                                                              @RequestParam(value = "format", required = true) String format,
                                                              @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                              @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdAndDateTimeBetweenReports")
    public String createAllAppointmentsByStaffIdAndDateTimeBetweenReports(Principal principal,
                                                                          @RequestParam(value = "format", required = true) String format,
                                                                          @RequestParam(value = "staffID", required = true) String staffID,
                                                                          @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                                          @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.setStaffID(staffID);
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAccessRecordReports")
    public String createAllAccessRecordReports(Principal principal,
                                               @RequestParam(value = "format", required = true) String format) {
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS, new ReportData(), ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordBetweenDateTimeReports")
    public String createAccessRecordBetweenDateTimeReports(Principal principal,
                                                           @RequestParam(value = "format", required = true) String format,
                                                           @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                           @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByStaffIdAndBetweenDateTimeReports")
    public String createAccessRecordByStaffIdAndBetweenDateTimeReports(Principal principal,
                                                                       @RequestParam(value = "format", required = true) String format,
                                                                       @RequestParam(value = "staffID", required = true) String staffID,
                                                                       @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                                       @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.setStaffID(staffID);
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByUserIdReports")
    public String createAccessRecordByUserIdReports(Principal principal,
                                                    @RequestParam(value = "format", required = true) String format,
                                                    @RequestParam(value = "staffID", required = true) String staffID) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format));
    }


    @Autowired
    private CalendarDBAdapter credentialRepository;


    @RequestMapping(method = RequestMethod.POST, value = "/getListOfUsers")
    public String getListOfUsers() {
        List<CredentialWrapper> credentials = credentialRepository.findAll();
        String returnPage = "";
        for (int i = 0; i < credentials.size(); i++) {

            // System.out.println(credentials.get(i));
            returnPage += "<option>" + credentials.get(i).getStaffID() + "</option>";
        }

        if (credentials.size() == 0) {
            returnPage += "<option>No active users of the system</option>";
        }

        return returnPage;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getListOfReports")
    public String getListOfReports() {

        String result = "";

        for (ReportFactory.reportTypes t : ReportFactory.reportTypes.values())
        {
            result += "<option>" + t.toString() + "</option>";
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getListOfFormats")
    public String getListOfFormats() {

        String result = "";

        for (ReportFormatter.Formats t : ReportFormatter.Formats.values())
        {
            result += "<option>" + t.toString() + "</option>";
        }

        return result;
    }



}
