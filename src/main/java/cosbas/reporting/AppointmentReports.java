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
        List<Appointment> data = IteratorUtils.toList(repo.findAll().iterator());
        report.setDataSource(data);
        return report;
    }

    public JasperReportBuilder createAllAppointmentsByStaffIdReports(String staffId)
    {
        JasperReportBuilder report = buildReport();
        List<Appointment> data = IteratorUtils.toList(repo.findByStaffID(staffId).iterator());
        report.setDataSource(data);
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

        StyleBuilder everything = Styles.style().setBorder(border).setVerticalAlignment(VerticalAlignment.MIDDLE);
        StyleBuilder singleValue = Styles.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(border).setVerticalAlignment(VerticalAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = Styles.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBold(true).setBorder(border).setVerticalAlignment(VerticalAlignment.MIDDLE);


        report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE).columns(
                Columns.column("Staff ID", "staffID", DataTypes.stringType()).setMinWidth(60),
                Columns.column("Visitor ID(s)", "visitorIDs", DataTypes.listType()).setMinWidth(90),
                Columns.column("Duration", "durationMinutes", DataTypes.integerType()).setFixedWidth(50).setStyle(singleValue),
                Columns.column("Summary", "summary", DataTypes.stringType()),
                Columns.column("Reason", "reason", DataTypes.stringType()),
                Columns.column("Status", "status", DataTypes.stringType()).setMinWidth(60).setStyle(singleValue)
        ).setColumnStyle(everything).setColumnTitleStyle(columnTitleStyle).title(Components.text("Testing").setHorizontalAlignment(HorizontalAlignment.CENTER));

        return report;


    }

}
