package cosbas.biometric.preprocessor;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.io.FileUtils;
import org.bytedeco.javacpp.opencv_core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.bytedeco.javacpp.opencv_highgui.imwrite;


/**
 * {@author Renette}
 * THis test can be manually done to check the correctness of the FacialProcesssing functions
 *
 * The two output iamges should look the same.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class FacialProcessingTest {

    FacialProcessing testee = new FacialProcessing();
    @Test
    public void testGrayscaleMat() throws Exception {
        final String folder = "./src/test/resources/";
        final String file = "grayscaletest.jpg";
        final String file2 = "grayscaleResult.png";
        final String file3 = "grayscaleResultB.png";

        Path path = Paths.get(folder + file);
        byte[] data = Files.readAllBytes(path);


        opencv_core.Mat step = testee.byteArrayToGrayscaleMat(data);
        byte[] newData =  testee.matToByteAray(step);

       BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(newData));

        ImageIO.write(bufferedImage, "png", new File(folder + file3));
        imwrite(folder + file2, step);
    }
}