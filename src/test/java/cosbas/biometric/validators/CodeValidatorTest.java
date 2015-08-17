package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Renette
 */
@RunWith(MockitoJUnitRunner.class)
public class CodeValidatorTest {

    BiometricDataDAO repository;
    CodeValidator testee;

    private static byte[] code1 = new byte[] {1,2,3,4,5};
    private static byte[] code2 = new byte[] {4,5,6,7,8};
    private static byte[] code3 = new byte[] {5,6,6,7,8};
    private static byte[] code4 = new byte[] {7,5,6,7,8};

    @Before
    public void setUp() throws Exception {
        repository = mock(BiometricDataDAO.class);
        testee = new CodeValidator();
        testee.setRepository(repository);
    }

    @Test
    public void testMatches() throws Exception {
        assertTrue(testee.matches(new AccessCode(code1), new AccessCode(code1), ));
        assertFalse(testee.matches(new AccessCode(code1), new AccessCode(code2), ));
    }

    /**
     * Test permanent and temporary access codes....
     * @throws Exception
     */
    @Test
    public void testValidate() throws Exception {
        ArrayList<BiometricData> list = new ArrayList<>(1);
        list.add(new AccessCode("tester1", code1));
        when(repository.findByData(code1)).thenReturn(list);

    }
}