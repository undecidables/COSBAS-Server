package cosbas.biometric.validators.facial;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.ValidationResponse;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@author Renette Ros}
 */
@RunWith(MockitoJUnitRunner.class)
public class FaceRecognitionTest {

    FaceRecognition testee;
    BiometricDataDAO biometricRepo;
    RecognizerDAO recognizerDAO;

    private final String folder = "./src/test/resources/faces/";
    private final String trainingFile = "upper6.txt";
    private final String testingfile = "lower3.txt";
    private List<BiometricData> trainingList = new LinkedList<>();

    @Before
    public void setUp() throws IOException {
        biometricRepo = mock(BiometricDataDAO.class);
        when(biometricRepo.findByType(BiometricTypes.FACE)).thenReturn(trainingList);
        recognizerDAO = mock(RecognizerDAO.class);
        testee = new FaceRecognition(recognizerDAO, biometricRepo);

        BufferedReader imgListFile = new BufferedReader(new FileReader(folder  + trainingFile));
        String line = imgListFile.readLine();
        while (!StringUtils.isEmpty(line)) {
            final String[] tokens = line.split(" ");
            int personNumber = Integer.parseInt(tokens[0]);
            String personName = tokens[1];
            String imgFilename = tokens[2];
            Path path = Paths.get(folder + imgFilename);
            byte[] data = Files.readAllBytes(path);

            BiometricData biometricData = new BiometricData(personName, BiometricTypes.FACE, data);
            trainingList.add(biometricData);

            line = imgListFile.readLine();

        }
    }

    @Test
    public void testRecognizeFace() throws Exception {
         testee.learn(trainingList);
        //assertNotNull(recognizerData);

        BufferedReader imgListFile = new BufferedReader(new FileReader(folder  + testingfile));
        String line = imgListFile.readLine();
        while (!StringUtils.isEmpty(line)) {
            final String[] tokens = line.split(" ");
            int personNumber = Integer.parseInt(tokens[0]);
            String personName = tokens[1];
            String imgFilename = tokens[2];
            Path path = Paths.get(folder + imgFilename);
            byte[] data = Files.readAllBytes(path);

            BiometricData biometricData = new BiometricData(BiometricTypes.FACE, data);
            ValidationResponse resp = testee.recognizeFace(biometricData);
            assertEquals(personName, resp.data);
            System.out.println(personName + " recognized with confidence " + resp.certainty);

            line = imgListFile.readLine();

        }
    }
}