package cosbas.web;

import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.CredentialWrapper;
import cosbas.logging.AuthenticateReports;
import cosbas.reporting.ReportData;
import cosbas.reporting.ReportFactory;
import cosbas.reporting.ReportFormatter;
import org.apache.commons.io.IOUtils;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
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


    private String getMimeType(InputStream is) throws IOException {
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = parser.getDetector();
        Metadata metadata = new Metadata();
        MediaType mediatype = detector.detect(is, metadata);
        return mediatype.toString();
    }

    private void sendOutputStream(HttpServletResponse response, InputStream stream) throws IOException {
        String mimeType = getMimeType(stream);
        if(mimeType == "null")
        {
            mimeType = "application/octet-stream";
        }
        IOUtils.copy(stream, response.getOutputStream());
        response.setContentType(mimeType);
        response.flushBuffer();
    }



    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsReport")
    public void createAllAppointmentsReport(Principal principal,
                                              @RequestParam(value = "format", required = true) String format, HttpServletResponse response) {
        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS, new ReportData(), ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdReports")
    public void createAllAppointmentsByStaffIdReports(Principal principal,
                                                        @RequestParam(value = "format", required = true) String format,
                                                        @RequestParam(value = "staffID", required = true) String staffID,
                                                        HttpServletResponse response) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStatusReports")
    public void createAllAppointmentsByStatusReports(Principal principal,
                                                       @RequestParam(value = "format", required = true) String format,
                                                       @RequestParam(value = "status", required = true) String status,
                                                       HttpServletResponse response) {
        ReportData data = new ReportData();
        data.setStatus(status);

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STATUS, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsBetweenDateTimeReports")
    public void createAllAppointmentsBetweenDateTimeReports(Principal principal,
                                                              @RequestParam(value = "format", required = true) String format,
                                                              @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                              @RequestParam(value = "dateTimeE", required = true) String dateTimeE,
                                                              HttpServletResponse response) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdAndDateTimeBetweenReports")
    public void createAllAppointmentsByStaffIdAndDateTimeBetweenReports(Principal principal,
                                                                          @RequestParam(value = "format", required = true) String format,
                                                                          @RequestParam(value = "staffID", required = true) String staffID,
                                                                          @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                                          @RequestParam(value = "dateTimeE", required = true) String dateTimeE,
                                                                          HttpServletResponse response) {

        ReportData data = new ReportData();
        data.setStaffID(staffID);
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAllAccessRecordReports")
    public void createAllAccessRecordReports(Principal principal,
                                               @RequestParam(value = "format", required = true) String format,
                                               HttpServletResponse response) {

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS, new ReportData(), ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordBetweenDateTimeReports")
    public void createAccessRecordBetweenDateTimeReports(Principal principal,
                                                           @RequestParam(value = "format", required = true) String format,
                                                           @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                           @RequestParam(value = "dateTimeE", required = true) String dateTimeE,
                                                           HttpServletResponse response) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByUserIdAndBetweenDateTimeReports")
    public void createAccessRecordByUserIdAndBetweenDateTimeReports(Principal principal,
                                                                       @RequestParam(value = "format", required = true) String format,
                                                                       @RequestParam(value = "staffID", required = true) String staffID,
                                                                       @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                                       @RequestParam(value = "dateTimeE", required = true) String dateTimeE,
                                                                       HttpServletResponse response) {

        ReportData data = new ReportData();
        data.setStaffID(staffID);
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @AuthenticateReports
    @RequestMapping(method= RequestMethod.POST, value="/createAccessRecordByUserIdReports")
    public void createAccessRecordByUserIdReports(Principal principal,
                                                  @RequestParam(value = "format", required = true) String format,
                                                  @RequestParam(value = "staffID", required = true) String staffID,
                                                  HttpServletResponse response) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);

        InputStream is = new ByteArrayInputStream(reports.getReport(ReportFactory.reportTypes.ALL_ACCESS_RECORDS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format)));
        try {
            sendOutputStream(response, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
