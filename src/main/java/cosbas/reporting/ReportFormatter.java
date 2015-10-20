package cosbas.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.stereotype.Service;

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

    public byte[] getFile(JasperReportBuilder report, Formats format)
    {
        long fileName = System.currentTimeMillis();
        byte[] file = null;
        try {

            switch (format)
            {
                case PDF:
                    report.toPdf(new FileOutputStream(fileName+""));
                    break;
                case HTML:
                    report.toHtml(new FileOutputStream(fileName + ""));
                    break;
                case WORD:
                    report.toDocx(new FileOutputStream(fileName + ""));
                    break;
                case CSV:
                    report.toCsv(new FileOutputStream(fileName + ""));
                    break;
                case EXCL:
                    report.toXlsx(new FileOutputStream(fileName + ""));
                    break;
                case XML:
                    report.toXml(new FileOutputStream(fileName + ""));
                    break;
                default:
                    break;
            }

            Path path = Paths.get(fileName+"");
            file = Files.readAllBytes(path);


        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
