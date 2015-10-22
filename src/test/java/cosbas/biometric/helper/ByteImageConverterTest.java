package cosbas.biometric.helper;


import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


/**
 * {@author Renette}
 * This test can be manually executed to check the correctness of the ImageConverter Classes
 */
public class ByteImageConverterTest {

    List<ByteImageConverter> converters = Arrays.asList(new IplByteConverter(), new MatByteConverter(), new CvMatByteConverter());

    private String encodingFormat = ".png";
    private static final String folder = "./src/test/resources/helper/";
    private static final String testFile = "grayscaletest.jpg";

    private static byte[] imageData;

    @BeforeClass
    public static void setUp() throws Exception {
        Path path = Paths.get(folder + testFile);
        imageData = Files.readAllBytes(path);

    }

    /**
     * All the iamges this test oputput should be scaled, grayscaled versions of the original.
     */
    @Test
    public void testScaleImage() throws Exception {
        System.out.print("HERE");
        for (int i =0; i<converters.size(); ++i) {
            final String resultFile = folder + "ResizedResult" + i + "." + encodingFormat;
            ByteImageConverter testee = converters.get(i);
            byte[] newData = testee.encodeBinary(testee.scaleImage(testee.createGrayScaleImage(imageData), 100, 150), encodingFormat);
            File file = new File(resultFile);
            FileUtils.writeByteArrayToFile(file, newData);
        }

    }

}