package cosbas.biometric.helper;


import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
public class ImageProcessorTest {

    List<ImageProcessor> converters = Arrays.asList(new IplProcessor(), new MatProcessor());

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
        int width = 100;
        int height = 150;

        System.out.print("HERE");
        for (int i =0; i<converters.size(); ++i) {
            final String resultFile = folder + "ResizedResult" + i + "." + encodingFormat;
            ImageProcessor testee = converters.get(i);


            Object image = testee.scaleImage(testee.grayScalefromBytes(imageData), width, height);

            Assert.assertEquals(testee.getWidth(image), width);
            Assert.assertEquals(testee.getHeight(image), height);

            byte[] newData = testee.toBytes(image, encodingFormat);
            File file = new File(resultFile);
            FileUtils.writeByteArrayToFile(file, newData);

        }

    }

}