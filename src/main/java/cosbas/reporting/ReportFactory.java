package cosbas.reporting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  {@author Szymon}
 */

@Service
public class ReportFactory {

    @Autowired
    AppointmentReports appointmentReports;

    @Autowired
    AccessRecordReports accessRecordReports;

    @Autowired
    ReportFormatter formatter;

    public static enum reportTypes
    {
        ALL_APPOINTMENTS,
        ALL_APPOINTMENTS_BY_STAFFID,
        ALL_APPOINTMENTS_BY_STATUS,
        ALL_APPOINTMENTS_BETWEEN_DATETIME,
        ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS,
        ALL_ACCESS_RECORDS_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS_BY_STAFFID
    }

    public byte[] getReport(reportTypes type, ReportData data, ReportFormatter.Formats format)
    {
        switch (type)
        {
            case ALL_APPOINTMENTS:
            case ALL_APPOINTMENTS_BETWEEN_DATETIME:
            case ALL_APPOINTMENTS_BY_STAFFID:
            case ALL_APPOINTMENTS_BY_STATUS:
            case ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME:
                return formatter.getFile(appointmentReports.getReport(type, data), format);
            case ALL_ACCESS_RECORDS:
            case ALL_ACCESS_RECORDS_BETWEEN_DATETIME:
            case ALL_ACCESS_RECORDS_BY_STAFFID:
            case ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME:
                return formatter.getFile(accessRecordReports.getReport(type, data), format);
            default:
                return null;
        }
    }

}
