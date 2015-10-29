package cosbas.biometric.validators.fingerprint;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.ValidationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by VivianWork on 10/27/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FingerprintMatchingTest {

    private final String path = "./src/test/resources/fingerprints/";
    private final String finger1 = "test100.bmp";

    private final String[] fingers = {"test600.bmp","test200.bmp","test400.bmp","test300.bmp","test500.bmp"};

    FingerprintMatching testee;
    FingerprintTemplateCreator creator;
    BufferedImage originalImage;
    BiometricDataDAO biometricRepo;
    private List<BiometricData> fingersList = new LinkedList<>();

    @org.junit.Before
    public void start() throws IOException {
        biometricRepo = mock(BiometricDataDAO.class);
        when(biometricRepo.findByUserID("BCrawley")).thenReturn(fingersList);

        originalImage = ImageIO.read(new File(path+finger1));
        byte[] dbItem = imagetobyte(originalImage);
        testee = new FingerprintMatching(dbItem,"BCrawley", 0.5, biometricRepo);

    }

    private byte[] imagetobyte(BufferedImage originalImage) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(originalImage,"bmp",byteArray);
        byteArray.flush();
        byte[] dbItem = byteArray.toByteArray();
        byteArray.close();

        return dbItem;
    }



    @Test
    public void testFingerprintMatching() throws IOException {
        creator = new FingerprintTemplateCreator();

        BufferedImage newImage;
        for (int i = 0; i < fingers.length; i++) {
            System.out.println("Counter: " + i);
            newImage = ImageIO.read(new File(path+fingers[i]));

            FingerprintTemplateData tester = creator.createTemplateFingerprintData("BCrawley",newImage,false, biometricRepo);
            ValidationResponse response = testee.matches(tester,"BCrawley", DoorActions.IN);

            if (response.approved) {
                System.out.println("Returned Certainty: " + response.certainty);
                return;
            } else {
                System.out.println("Returned Certainty too low try again: " + response.certainty);
            }
        }
    }

    /*@Test
    public void testFingerprintDB() throws IOException {
        creator = new FingerprintTemplateCreator();
        BufferedImage newImage = ImageIO.read(new File(path+fingers[3]));

        if (biometricRepo == null) {
            System.out.println("REPO IS NULL!!!");
        }

        FingerprintTemplateData tester = creator.createTemplateFingerprintData("BCrawley", newImage, true, biometricRepo);

        List<BiometricData> tester1 = biometricRepo.findByUserID("BCrawley");

        if(tester1 != null) {
            System.out.println("Tester1 null");

            FingerprintTemplateData finger = (FingerprintTemplateData) tester1.get(0);

            if (finger != null) {

                ArrayList<Point> bi = finger.getBifurcations();
                System.out.println(bi.get(0));

            }
        }
        return;

    }*/

}
