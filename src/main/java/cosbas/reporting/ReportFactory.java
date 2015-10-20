package cosbas.reporting;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *  {@author Szymon}
 */
public class ReportFactory {

    @Autowired
    AppointmentReports appointmentReports;

    @Autowired
    AccessRecordReports accessRecordReports;

    public static enum reportTypes
    {
        ALL_APPOINTMENTS,
        ALL_APPOINTMENTS_BY_STAFFID,
        ALL_APPOINTMENTS_BY_STATUS,
        ALL_APPOINTMENTS_BETWEEN_DATETIME,
        ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS,
        ALL_ACCESS_RECORDS_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS_BY_USERID_AND_BETWEEN_DATETIME,
        ALL_ACCESS_RECORDS_BY_USERID
    }

    public byte[] getReport(reportTypes type, ReportData data, String format)
    {
        switch (type)
        {
            case ALL_APPOINTMENTS:
            case ALL_APPOINTMENTS_BETWEEN_DATETIME:
            case ALL_APPOINTMENTS_BY_STAFFID:
            case ALL_APPOINTMENTS_BY_STATUS:
            case ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME:
            default:
                return null;
        }
    }

}
