package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


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
    private AccessCode req1 = new AccessCode(code1);
    private AccessCode req2 = new AccessCode(code2);
    private String user1 = "user";

    @Before
    public void setUp() throws Exception {
        repository = mock(BiometricDataDAO.class);
        testee = new CodeValidator();
        testee.setRepository(repository);

        LocalDateTime now = LocalDateTime.now();

        before1 = now.minusHours(1);
        before2 = now.minusHours(2);
        after1 = now.plusHours(1);
        after2 = now.plusHours(2);

    }

    @Test
    public void testMatches() throws Exception {

        ValidationResponse expected = ValidationResponse.successfulValidation(user1);

        /** Permanent Access Code valid */
        AccessCode valid1 = new AccessCode(user1, code1);
        assertEquals("Permanent Access Code in", testee.matches(req1, valid1, DoorActions.IN), expected);
        assertEquals("Permanent Access Code out", testee.matches(req1, valid1, DoorActions.OUT), expected);
        /** Test if response is false when invalid code */
        assertFalse("In: Invalid code", testee.matches(req2, valid1, DoorActions.IN).approved);
        assertFalse("Out: Invalid code", testee.matches(req2, valid1, DoorActions.IN).approved);

        /** Testing Temporary access code valid in correct time*/
        valid1 = new AccessCode(user1, code1, before1, after1);

        assertEquals("In: Temporary access code valid", testee.matches(req1, valid1, DoorActions.IN), expected);
        assertEquals("Out: Temporary access code valid", testee.matches(req1, valid1, DoorActions.OUT), expected);
        /** Test if response is false when invalid & comparing against temporary code */
        assertFalse("In: Invalid code", testee.matches(req2, valid1, DoorActions.IN).approved);
        assertFalse("Out: Invalid code", testee.matches(req2, valid1, DoorActions.IN).approved);

        /** Test expired code */
        valid1 = new AccessCode(user1, code1, before2, before1);
        assertFalse("Entrance when code expired.", testee.matches(req1, valid1, DoorActions.IN).approved);
        /** Expired code should allow user to exit */
        assertEquals("Exit when code expired.", testee.matches(req1, valid1, DoorActions.OUT), expected);

        /** Future code should not allow user to enter or exit */
        valid1 = new AccessCode(user1, code1, after1, after2);
        assertFalse("In: Future Code", testee.matches(req1, valid1, DoorActions.IN).approved);
        assertFalse("Out: Future Code", testee.matches(req1, valid1, DoorActions.OUT).approved);


    }

    /**
     * Test permanent and temporary access codes....
     * @throws Exception
     */
    @Test
    public void testValidate() throws Exception {
        testee = spy(testee);
        AccessCode dbCode = new AccessCode("user", code1);

        when(repository.findByData(code1)).thenReturn(dbCode);

        /**
         * Mock matches method to return positive
         */
        ValidationResponse matches = ValidationResponse.successfulValidation(user1);

        doReturn(matches).when(testee).matches(any(BiometricData.class), any(BiometricData.class), any(DoorActions.class));

        for (DoorActions testAction : DoorActions.values()) {
            ValidationResponse resp = testee.validate(req1, testAction);
            assertEquals(resp, matches);
            assertEquals("Last action changed when validation succeeds. " , dbCode.getLastAction(), testAction);
        }

        /**
         * Mock matches method to return negative
         */

        matches = ValidationResponse.failedValidation("Failure");
        doReturn(matches).when(testee).matches(any(BiometricData.class), any(BiometricData.class), any(DoorActions.class));


        for (DoorActions testAction : DoorActions.values()) {
            DoorActions prevAction = dbCode.getLastAction();
            ValidationResponse resp = testee.validate(req1, testAction);
            assertEquals(resp, matches);
            assertTrue("Last action unchanged when validation failed. ", dbCode.getLastAction() == prevAction);
        }

        ArrayList<BiometricTypes> invalidTypes = new ArrayList<>(Arrays.asList(BiometricTypes.values()));
        invalidTypes.remove(BiometricTypes.CODE);
        for (BiometricTypes type : invalidTypes) {
            for (DoorActions action : DoorActions.values()) {
                try {
                    BiometricData test = new BiometricData(type, new byte[]{1, 2, 3});
                    testee.validate(test, action);
                    fail("Exception failed for Type: " + type + " and Action: " + action);
                } catch (BiometricTypeException ignored) {}
            }

        }

    }

    @Test
    public void testIsDuplicate() throws Exception {

    }
}