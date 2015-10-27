package cosbas.biometric.validators.fingerprint;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by VivianWork on 10/27/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FingerprintMatchingTest {

    private final String folder = "./src/test/resources/fingerprints";

    FingerprintMatching testee;
    BiometricDataDAO biometricRepo;
    private List<BiometricData> trainingList = new LinkedList<>();

    /*@Before
    public void start() {
        biometricRepo = mock(BiometricDataDAO.class);
        when(biometricRepo.findByUserID("BCrawley")).thenReturn(trainingList);
    }*/



    @Test
    public void testFingerprintMatching() {


    }

}
