package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Renette
 */
@RunWith(MockitoJUnitRunner.class)
public class CodeValidatorTest {

    BiometricDataDAO repository;
    CodeValidator testee;

    private static final byte[] code1 = new byte[] {1,2,3,4,5};
    private static final byte[] code2 = new byte[] {4,5,6,7,8};
    private LocalDateTime before1;
    private LocalDateTime before2;
    private LocalDateTime after1;
    private LocalDateTime after2;
    private AccessCode req1 = new AccessCode(code1);;
    private AccessCode req2 = new AccessCode(code2);;

    @Before
    public void setUp() throws Exception {
        repository = mock(BiometricDataDAO.class);
        testee = spy(CodeValidator.class);
        testee.setRepository(repository);

        LocalDateTime now = LocalDateTime.now();

        before1 = now.minusHours(1);
        before2 = now.minusHours(2);
        after1 = now.plusHours(1);
        after2 = now.plusHours(2);

    }

    @Test
    public void testMatches() throws Exception {
        AccessCode valid = new AccessCode("user", code1);

        /* Permanent Access Code valid */
        assertEquals("Permanent Access Code in", testee.matches(req1, valid, "in"), valid.getPersonID());
        assertEquals("Permanent Access Code out", testee.matches(req1, valid, "out"), valid.getPersonID());

        /* Test if exception is thrown when code is invalid */
        try {
            testee.matches(req2, valid, "in");
            fail("Wrong Code, in: Exception not thrown");
        } catch (ValidationException ignored) {}
        try {
            testee.matches(req2, valid, "out");
            fail("Wrong Code, out: Exception not thrown");
        } catch (ValidationException ignored) {}

        /* Testing Temporary access code valid */
        valid = new AccessCode("user", code1, before1, after1);
        assertEquals("In: Temporary access code valid", testee.matches(req1, valid, "in"),  valid.getPersonID());
        assertEquals("Out: Temporary access code valid", testee.matches(req1, valid, "out"), valid.getPersonID());

        valid = new AccessCode("user", code1, before2, before1);
        try {
            testee.matches(req1, valid, "in");
            fail("Code Entrance Expired: Exception not thrown");
        } catch (ValidationException ignored) {}
        assertEquals("Code", testee.matches(req1, valid, "out"), valid.getPersonID());

        valid = new AccessCode("user", code1, after1, after2);
        try {
            testee.matches(req1, valid, "in");
            fail("Future Code, in: Exception not thrown");
        } catch (ValidationException ignored) {}
        try {
            testee.matches(req1, valid, "out");
            fail("Future Code, out: Exception not thrown");
        } catch (ValidationException ignored) {}
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