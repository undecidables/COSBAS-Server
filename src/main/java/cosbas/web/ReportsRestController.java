package cosbas.web;

import cosbas.reporting.ReportData;
import cosbas.reporting.ReportFactory;
import cosbas.reporting.ReportFormatter;
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
    public byte[] createAllAppointmentsReport(@RequestParam(value = "format", required = true) String format) {
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS, new ReportData(), ReportFormatter.Formats.valueOf(format));
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdReports")
    public byte[] createAllAppointmentsByStaffIdReports(@RequestParam(value = "format", required = true) String format,
                                                        @RequestParam(value = "staffID", required = true) String staffID) {
        ReportData data = new ReportData();
        data.setStaffID(staffID);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID, data, ReportFormatter.Formats.valueOf(format));
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStatusReports")
    public byte[] createAllAppointmentsByStatusReports(@RequestParam(value = "format", required = true) String format,
                                                       @RequestParam(value = "status", required = true) String status) {
        ReportData data = new ReportData();
        data.setStatus(status);
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STATUS, data, ReportFormatter.Formats.valueOf(format));
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsBetweenDateTimeReports")
    public byte[] createAllAppointmentsBetweenDateTimeReports(@RequestParam(value = "format", required = true) String format,
                                                              @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                              @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="/createAllAppointmentsByStaffIdAndDateTimeBetweenReports")
    public byte[] createAllAppointmentsByStaffIdAndDateTimeBetweenReports(@RequestParam(value = "format", required = true) String format,
                                                                          @RequestParam(value = "staffID", required = true) String staffID,
                                                                          @RequestParam(value = "dateTimeS", required = true) String dateTimeS,
                                                                          @RequestParam(value = "dateTimeE", required = true) String dateTimeE) {

        ReportData data = new ReportData();
        data.setStaffID(staffID);
        data.seteDate(LocalDateTime.parse(dateTimeE, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        data.setsDate(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return reports.getReport(ReportFactory.reportTypes.ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME, data, ReportFormatter.Formats.valueOf(format));
    }


}
