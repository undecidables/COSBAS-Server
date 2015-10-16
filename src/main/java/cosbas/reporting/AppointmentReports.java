package cosbas.reporting;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by simon on 2015-10-16.
 */
@Service
public class AppointmentReports {

    @Autowired
    private AppointmentDBAdapter repo;


    public void listAllAppointments()
    {
        JasperReportBuilder report = DynamicReports.report();

        List<Appointment> data = IteratorUtils.toList(repo.findAll().iterator());

        report.columns(
                Columns.column("Staff ID", "staffID", DataTypes.stringType()),
                Columns.column("Visitor ID(s)", "visitorIDs", DataTypes.listType()),
                Columns.column("Duration", "durationMinutes", DataTypes.integerType()),
                Columns.column("Reason", "reason", DataTypes.stringType())
        ).title(Components.text("Testing").setHorizontalAlignment(HorizontalAlignment.CENTER)).setDataSource(data);
        try {
            report.toPdf(new FileOutputStream("t.pdf"));
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
