package cosbas.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public String getFile(JasperReportBuilder report, Formats format)
    {
        File dir = new File("downloads/reports/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        String path = "downloads/reports/";
        String fileName = "" + System.currentTimeMillis();
        try {

            switch (format)
            {
                case PDF:
                    report.toPdf(new FileOutputStream(path + fileName+""));
                    break;
                case HTML:
                    report.toHtml(new FileOutputStream(path + fileName + ""));
                    break;
                case WORD:
                    report.toDocx(new FileOutputStream(path + fileName + ""));
                    break;
                case CSV:
                    report.toCsv(new FileOutputStream(path + fileName + ""));
                    break;
                case EXCL:
                    report.toXlsx(new FileOutputStream(path + fileName + ""));
                    break;
                case XML:
                    report.toXml(new FileOutputStream(path + fileName + ""));
                    break;
                default:
                    break;
            }



        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
