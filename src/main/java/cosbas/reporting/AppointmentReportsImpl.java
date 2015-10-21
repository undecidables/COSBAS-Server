package cosbas.reporting;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.BorderBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

/**
 *  {@author Szymon}
 */
@Component
public class AppointmentReportsImpl implements AppointmentReports {

    @Autowired
    private AppointmentDBAdapter repository;


    private JasperReportBuilder createAllAppointmentsReports()
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findAll().iterator())));
        return report;
    }

    private JasperReportBuilder createAllAppointmentsByStaffIdReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByStaffID(data.getStaffID()).iterator())));
        return report;
    }

    private JasperReportBuilder createAllAppointmentsByStatusReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByStatusLike(data.getStatus()).iterator())));
        return report;
    }

    private JasperReportBuilder createAllAppointmentsBetweenDateTimeReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByDateTimeBetween(data.getsDate(), data.geteDate()).iterator())));
        return report;
    }

    private JasperReportBuilder createAllAppointmentsByStaffIdAndDateTimeBetweenReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByStaffIDAndDateTimeBetween(data.getStaffID(), data.getsDate(), data.geteDate()).iterator())));
        return report;
    }

    private JasperReportBuilder buildReport()
    {
        JasperReportBuilder report = DynamicReports.report();

        BorderBuilder border = Styles.border(Styles.pen().setLineStyle(LineStyle.SOLID).setLineColor(Color.BLACK).setLineWidth((float) 0.5));

        StyleBuilder everything = Styles.style().setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setLeftIndent(10);
        StyleBuilder singleValue = Styles.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = Styles.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBold(true).setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE).columns(
                Columns.column("Staff ID", "staffId", DataTypes.stringType()).setMinWidth(60),
                Columns.column("Visitor ID(s)", "visitorIds", DataTypes.stringType()).setMinWidth(90),
                Columns.column("Date Time", "dateTime", DataTypes.stringType()).setFixedWidth(90).setStyle(singleValue),
                Columns.column("Duration", "duration", DataTypes.integerType()).setFixedWidth(50).setStyle(singleValue),
                Columns.column("Summary", "summary", DataTypes.stringType()),
                Columns.column("Reason", "reason", DataTypes.stringType()),
                Columns.column("Status", "status", DataTypes.stringType()).setMinWidth(60).setStyle(singleValue)
        ).setColumnStyle(everything).setColumnTitleStyle(columnTitleStyle).title(Components.text("Testing").setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));

        return report;
    }

    private DRDataSource createDataSource(List<Appointment> data)
    {
        DRDataSource dataSource = new DRDataSource("staffId","visitorIds","dateTime","duration","summary","reason","status");
        for(Appointment appointment : data)
        {
            dataSource.add(appointment.getStaffID(), appointment.getListOfVisitors(), appointment.getDateTime().toLocalDate().toString() + " " + appointment.getDateTime().toLocalTime().minusSeconds(30).toString(), appointment.getDurationMinutes(), appointment.getSummary(), appointment.getReason(), appointment.getStatus());
        }

        return dataSource;
    }

    @Override
    public JasperReportBuilder getReport(ReportFactory.reportTypes type, ReportData data)
    {
        switch (type)
        {
            case ALL_APPOINTMENTS:
                return createAllAppointmentsReports();
            case ALL_APPOINTMENTS_BETWEEN_DATETIME:
                return createAllAppointmentsBetweenDateTimeReports(data);
            case ALL_APPOINTMENTS_BY_STAFFID:
                return createAllAppointmentsByStaffIdReports(data);
            case ALL_APPOINTMENTS_BY_STATUS:
                return createAllAppointmentsByStatusReports(data);
            case ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME:
                return createAllAppointmentsByStaffIdAndDateTimeBetweenReports(data);
            default:
                return null;
        }
    }

}
