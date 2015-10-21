package cosbas.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

/**
 * Created by Renette on 2015-10-21.
 */
public interface AppointmentReports extends ReportInterface {
    JasperReportBuilder getReport(ReportFactory.reportTypes type, ReportData data);
}
