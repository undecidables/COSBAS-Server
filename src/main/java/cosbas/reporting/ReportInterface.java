package cosbas.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.jasperreports.engine.JasperReport;

/**
 *  {@author Szymon}
 */
public interface ReportInterface {
    public JasperReportBuilder getReport(ReportFactory.reportTypes type, ReportData data);
}
