package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.*;
import cosbas.biometric.request.access.DoorActions;
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

    private static final byte[] code1 = new byte[]{1, 2, 3, 4, 5};
    private static final byte[] code2 = new byte[]{4, 5, 6, 7, 8};
    BiometricDataDAO repository;
    CodeValidator testee;
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

        ValidationResponse resp;
        AccessCode dbItem1 = new AccessCode(user1, code1);
        for (DoorActions testAction : DoorActions.values()) {
            /** Permanent Access Code valid */
            resp = testee.matches(req1, dbItem1, testAction);
            assertMatchesSuccess(resp, dbItem1, "Matches: Valid permanent access code.");

            /** Test if response is false when invalid code */
            resp = testee.matches(req2, dbItem1, testAction);
            assertMatchesFailure(resp, dbItem1, "Matches: Invalid code");
        }

        dbItem1 = new TemporaryAccessCode(user1, code1, before1, after1, "0");
        for (DoorActions testAction : DoorActions.values()) {

            /** Testing Temporary access code valid in correct time*/
            resp = testee.matches(req1, dbItem1, testAction);
            assertMatchesSuccess(resp, dbItem1,  "Matches: Temporary access code valid");

            /** Test if response is false when invalid & comparing against temporary code */
            resp = testee.matches(req2, dbItem1, testAction);
            assertMatchesFailure(resp, dbItem1,  "Matches: Invalid code & time correct");
        }

        dbItem1 = new TemporaryAccessCode(user1, code1, after1, after2, "0");
        for (DoorActions testAction : DoorActions.values()) {
            /** Future code should not allow user to enter or exit */
            resp = testee.matches(req1, dbItem1, testAction);
            assertMatchesFailure(resp, dbItem1,  "Matches: Future Code");
        }

        /** Test expired code */
        dbItem1 = new TemporaryAccessCode(user1, code1, before2, before1, "0");

        assertMatchesFailure(testee.matches(req1, dbItem1, DoorActions.IN), dbItem1, "Matches: Entrance when code expired.");
        /** Expired code should allow user to exit */
        assertMatchesSuccess(testee.matches(req1, dbItem1, DoorActions.OUT), dbItem1,  "Matches: Exit when code expired");

    }


    @Test
    public void testValidate() throws Exception {
        testee = spy(testee);
        AccessCode accessCode = new AccessCode(user1, code1);


        ArrayList<BiometricTypes> invalidTypes = new ArrayList<>(Arrays.asList(BiometricTypes.values()));
        invalidTypes.remove(BiometricTypes.CODE);
        when(repository.findByData(code1)).thenReturn(accessCode);
        when(repository.findByData(code2)).thenReturn(null);


        /**
         * Success ful validation:
         *  - Last testAction changed.
         *  - Success Repose
         *
         * Failed validation:
         *  - Last testAction unchanged.
         *  - Failure Response.
         */
        ValidationResponse resp;
        DoorActions prevAction;
        for (DoorActions testAction : DoorActions.values()) {

            /** Permanent access codes */

            resp = testee.validate(req1, testAction);
            assertValidateSuccess(resp, accessCode, testAction, "Permanent code correct");
        }

        TemporaryAccessCode dbCode = spy(new TemporaryAccessCode(user1, code1, after1, after2, "appointment"));
        when(repository.findByData(code1)).thenReturn(dbCode);
        for (DoorActions testAction : DoorActions.values())       {
            /**
             * Too Early
             */

            doReturn(after1).when(dbCode).getValidFrom();
            doReturn(after2).when(dbCode).getValidTo();
            prevAction = dbCode.getLastAction();
            resp = testee.validate(req1, testAction);
            assertValidateFailure(resp, dbCode, prevAction, "Validate too early");

            /**
             * On Time
             */
            doReturn(before2).when(dbCode).getValidFrom();
            resp = testee.validate(req1, testAction);
            assertValidateSuccess(resp, dbCode, testAction, "Validate in time");


            /**
             * Not in db
             */
            resp = testee.validate(req2, testAction);
            assertFalse("Not in db", resp.approved);

            for (BiometricTypes type : invalidTypes) {
                try {
                    BiometricData test = new BiometricData(type, new byte[]{1, 2, 3});
                    testee.validate(test, testAction);
                    fail("Exception not thrown for Type: " + type + " and Action: " + testAction);
                } catch (BiometricTypeException ignored) {
                }
            }
        }

        /**
         * Too late
         */
        doReturn(before1).when(dbCode).getValidTo();
        doReturn(before2).when(dbCode).getValidFrom();
        prevAction = accessCode.getLastAction();
        resp = testee.validate(req1, DoorActions.IN);
        assertValidateFailure(resp, dbCode, prevAction, "Validate: Enter with Expired code.");

        resp = testee.validate(req1, DoorActions.OUT);
        assertValidateSuccess(resp, dbCode, DoorActions.OUT, "Validate: Exit with Expired code.");


    }

    private void assertValidateFailure(ValidationResponse resp, AccessCode dbCode, DoorActions prevAction, String message) {
        assertFalse(message, resp.approved);
        assertEquals(message, dbCode.getLastAction(), prevAction);
    }

    private void assertValidateSuccess(ValidationResponse resp, AccessCode dbCode, DoorActions testAction, String message) {
        assertTrue(message, resp.approved);
        assertEquals(message, resp.data, user1);
        assertEquals(message, dbCode.getLastAction(), testAction);
    }

    private void assertMatchesSuccess(ValidationResponse resp, AccessCode dbCode, String message) {
        assertTrue(message, resp.approved);
        assertEquals(resp.data, user1);
    }

    private void assertMatchesFailure(ValidationResponse resp, AccessCode dbCode, String message) {
        assertFalse(message, resp.approved);
    }


    @Test
    public void testIsDuplicate() throws Exception {

    }
}