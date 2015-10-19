package cosbas.reporting;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.BorderBuilder;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.collections.IteratorUtils;
import org.bytedeco.javacpp.opencv_objdetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.transform.Templates;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by simon on 2015-10-16.
 */
@Service
public class AppointmentReports {

    @Autowired
    private AppointmentDBAdapter repo;


    public JasperReportBuilder createAllAppointmentsReports()
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repo.findAll().iterator())));
        return report;
    }

    public JasperReportBuilder createAllAppointmentsByStaffIdReports(String staffId)
    {
        JasperReportBuilder report = buildReport();
      //  report.setDataSource(createDataSource(IteratorUtils.toList(repo.findByStaffID(staffId).iterator())));
        return report;
    }

    public JasperReportBuilder createAllAppointmentsByDateTime(LocalDateTime dateTime)
    {
        JasperReportBuilder report = buildReport();
       /* List<Appointment> data = IteratorUtils.toList(repo.findByDateTime(dateTime).iterator());
        report.setDataSource(data);*/
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
        ).setColumnStyle(everything).setColumnTitleStyle(columnTitleStyle).title(Components.text("Testing").setHorizontalAlignment(HorizontalAlignment.CENTER));

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

}
