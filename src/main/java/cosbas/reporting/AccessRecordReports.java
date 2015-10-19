package cosbas.reporting;

import cosbas.appointment.AppointmentDBAdapter;
import cosbas.biometric.request.access.AccessRecord;
import cosbas.biometric.request.access.AccessRecordDAO;
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
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Szymon}
 */

@Service
public class AccessRecordReports {

    @Autowired
    private AccessRecordDAO repository;

    public JasperReportBuilder createAllAccessRecordReports()
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findAll().iterator())));
        return report;
    }

    public JasperReportBuilder createAccessRecordBetweenDateTimeReports(LocalDateTime dateS, LocalDateTime dateE)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByDateTimeBetween(dateS, dateE).iterator())));
        return report;
    }

    public JasperReportBuilder createAccessRecordByUserIdAndDateTimeBetweenReports(String userId, LocalDateTime dateS, LocalDateTime dateE)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByUserIDAndDateTimeBetween(userId, dateS, dateE).iterator())));
        return report;
    }

    private JasperReportBuilder buildReport()
    {
        JasperReportBuilder report = DynamicReports.report();

        BorderBuilder border = Styles.border(Styles.pen().setLineStyle(LineStyle.SOLID).setLineColor(Color.BLACK).setLineWidth((float) 0.5));

        StyleBuilder everything = Styles.style().setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setLeftIndent(10);
        StyleBuilder singleValue = Styles.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        StyleBuilder columnTitleStyle = Styles.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBold(true).setBorder(border).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT).columns(
                Columns.column("Door ID", "doorId", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue),
                Columns.column("Action", "action", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue),
                Columns.column("User ID", "userId", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue),
                Columns.column("Date Time", "dateTime", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue)
        ).setColumnStyle(everything).setColumnTitleStyle(columnTitleStyle).title(Components.text("Testing").setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));


        return report;
    }

    private DRDataSource createDataSource(List<AccessRecord> data)
    {
        DRDataSource dataSource = new DRDataSource("doorId","action","userId","dateTime");
        for(AccessRecord accessRecord : data)
        {
            dataSource.add(accessRecord.getDoorID(), accessRecord.getAction().toString(), accessRecord.getUserID(), accessRecord.getDateTime().toLocalDate().toString() + " " + accessRecord.getDateTime().toLocalTime().minusSeconds(30).toString());
        }

        return dataSource;
    }
}
