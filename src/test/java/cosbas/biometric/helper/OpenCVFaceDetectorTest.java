package cosbas.biometric.helper;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


import java.io.File;


/**
 * {@author Renette}
 */
public class OpenCVFaceDetectorTest {
    private static final String trainFile = "./src/main/resources/faces/haarcascade_frontalface_alt.xml";
    private static final String facesFile = "./src/test/resources/helper/faces.jpg";
    private static final String resultFile = "./src/test/resources/helper/facesResult.png";

    @Test
    public void testDetectFaces() throws Exception {
        OpenCVFaceDetector testee = new OpenCVFaceDetector(new MatProcessor(), trainFile);
        testee.constructClassifier();
        byte[] image = FileUtils.readFileToByteArray(new File(facesFile));
        byte[] res = testee.getCenterFace(image, "png");
         FileUtils.writeByteArrayToFile(new File(resultFile), res);
    }
}