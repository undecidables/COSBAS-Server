package cosbas.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  {@author Szymon}
 */

@Service
public class ReportFormatter {

    public static enum Formats
    {
        PDF,
        HTML,
        EXCL,
        CSV,
        WORD,
        XML
    }

    public byte[] getFile(JasperReportBuilder report, Formats format)
    {
        ByteArrayOutputStream file = new ByteArrayOutputStream();
        try {

            switch (format)
            {
                case PDF:
                    report.toPdf(file);
                    break;
                case HTML:
                    report.toHtml(file);
                    break;
                case WORD:
                    report.toDocx(file);
                    break;
                case CSV:
                    report.toCsv(file);
                    break;
                case EXCL:
                    report.toXlsx(file);
                    break;
                case XML:
                    report.toXml(file);
                    break;
                default:
                    break;
            }



        } catch (DRException e) {
            e.printStackTrace();
        }
        return file.toByteArray();
    }

}
