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

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsReport")
    public byte[] createAllAppointmentsReport(Principal principal,
                                              @RequestParam(value = "format", required = true) String format, HttpServletResponse response) {
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS, new ReportData(), ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdReports")
    public byte[] createAllAppointmentsByStaffIdReports(Principal principal,
                                                        @RequestParam(value = "format", required = true) String format,
                                                        @RequestParam(value = "staffID", required = true) String staffID) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStatusReports")
    public byte[] createAllAppointmentsByStatusReports(Principal principal,
                                                       @RequestParam(value = "format", required = true) String format,
                                                       @RequestParam(value = "status", required = true) String status) {
        ReportData data = new ReportData();
        data.setStatus(status);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STATUS, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsBetweenDateTimeReports")
    public byte[] createAllAppointmentsBetweenDateTimeReports(Principal principal,
                                                              @RequestParam(value = "format", required = true) String format,
                                                              @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                              @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdAndDateTimeBetweenReports")
    public byte[] createAllAppointmentsByStaffIdAndDateTimeBetweenReports(Principal principal,
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
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAccessRecordReports")
    public byte[] createAllAccessRecordReports(Principal principal,
                                               @RequestParam(value = "format", required = true) String format) {
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS, new ReportData(), ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordBetweenDateTimeReports")
    public byte[] createAccessRecordBetweenDateTimeReports(Principal principal,
                                                           @RequestParam(value = "format", required = true) String format,
                                                           @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                           @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @AuthenticateReports
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByStaffIdAndBetweenDateTimeReports")
    public byte[] createAccessRecordByStaffIdAndBetweenDateTimeReports(Principal principal,
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
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByUserIdReports")
    public byte[] createAccessRecordByUserIdReports(Principal principal,
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
