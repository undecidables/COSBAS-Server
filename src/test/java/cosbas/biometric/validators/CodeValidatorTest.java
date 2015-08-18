package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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
    private LocalDateTime now;
    private LocalDateTime before1;
    private LocalDateTime before2;
    private LocalDateTime after1;
    private LocalDateTime after2;

    @Before
    public void setUp() throws Exception {
        repository = mock(BiometricDataDAO.class);
        testee = spy(CodeValidator.class);
        testee.setRepository(repository);

        now = LocalDateTime.now();

        before1 = now.minusHours(1);
        before2 = now.minusHours(2);
        after1 = now.plusHours(1);
        after2 = now.plusHours(2);
    }

    @Test
    public void testMatches() throws Exception {


        AccessCode valid1 = new AccessCode("u0000000", code1);
        AccessCode req1 = new AccessCode(code1);
        AccessCode req2 = new AccessCode(code2);

        AccessCode valid2 = new AccessCode("tempUser", code2, before1, after1);
        //when(repository.findByData(code1)).thenReturn(valid1)

        /* Permanent Access Code valid1 */
        assertEquals(testee.matches(req1, valid1, "in").data, valid1.getPersonID());
        assertEquals(testee.matches(req1, valid1, "out").data, valid1.getPersonID());

        /* Test if exception is thrown when code is invalid */
        try {
            testee.matches(req2, valid1, "in");
            fail();
        } catch (ValidationException ignored) {}
        try {
            testee.matches(req2, valid1, "out");
            fail();
        } catch (ValidationException ignored) {}

        /* Testing Temporary access code valid */
    }

    /**
     * Test permanent and temporary access codes....
     * @throws Exception
     */
    @Test
    public void testValidate() throws Exception {
        AccessCode code = new AccessCode("tester1", code1);
        when(repository.findByData(code1)).thenReturn(code);
    }

    @Test
    public void testIsDuplicate() throws Exception {

    }
}