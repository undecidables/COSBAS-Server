package cosbas.reporting;

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
import java.util.List;

/**
 *  {@author Szymon}
 */

@Service
public class AccessRecordReports implements ReportInterface {


    @Autowired
    private AccessRecordDAO repository;

    private JasperReportBuilder createAllAccessRecordReports()
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findAll().iterator())));
        return report;
    }

    private JasperReportBuilder createAccessRecordBetweenDateTimeReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByDateTimeBetween(data.getsDate(), data.geteDate()).iterator())));
        return report;
    }

    private JasperReportBuilder createAccessRecordByUserIdAndBetweenDateTimeReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByUserIDAndDateTimeBetween(data.getStaffID(), data.getsDate(), data.geteDate()).iterator())));
        return report;
    }

    private JasperReportBuilder createAccessRecordByUserIdReports(ReportData data)
    {
        JasperReportBuilder report = buildReport();
        report.setDataSource(createDataSource(IteratorUtils.toList(repository.findByUserID(data.getStaffID()).iterator())));
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
                Columns.column("User ID", "staffId", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue),
                Columns.column("Date Time", "dateTime", DataTypes.stringType()).setMinWidth(90).setStyle(singleValue)
        ).setColumnStyle(everything).setColumnTitleStyle(columnTitleStyle).title(Components.text("Access Record").setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));


        return report;
    }

    private DRDataSource createDataSource(List<AccessRecord> data)
    {
        DRDataSource dataSource = new DRDataSource("doorId","action","staffId","dateTime");
        for(AccessRecord accessRecord : data)
        {
            dataSource.add(accessRecord.getDoorID(), accessRecord.getAction().toString(), accessRecord.getUserID(), accessRecord.getDateTime().toLocalDate().toString() + " " + accessRecord.getDateTime().toLocalTime().minusSeconds(30).toString());
        }

        return dataSource;
    }

    public JasperReportBuilder getReport(ReportFactory.reportTypes type, ReportData data)
    {
        switch (type)
        {
            case ALL_ACCESS_RECORDS:
                return createAllAccessRecordReports();

            case ALL_ACCESS_RECORDS_BETWEEN_DATETIME:
                return createAccessRecordBetweenDateTimeReports(data);

            case ALL_ACCESS_RECORDS_BY_STAFFID:
                return createAccessRecordByUserIdReports(data);

            case ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME:
                return createAccessRecordByUserIdAndBetweenDateTimeReports(data);

            default:
                return null;
        }
    }
}
