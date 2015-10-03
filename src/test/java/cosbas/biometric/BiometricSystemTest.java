package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Renette
 */
public class BiometricSystemTest {

    private BiometricSystem testee;
    private ValidatorFactory factoryMock;
    private BiometricDataDAO personRepoMock;
    private AccessValidator validator;
    private BiometricData data1;
    private BiometricData data2;
    private ValidationResponse successU1;
    private ValidationResponse successU2;
    private ValidationResponse fail;

    @Before
    public void setUp() throws Exception {
        testee = new BiometricSystem();
        factoryMock = mock(ValidatorFactory.class);
        personRepoMock = mock(BiometricDataDAO.class);
        testee.setFactory(factoryMock);
        testee.setBiometricDataRepository(personRepoMock);
        validator = mock(AccessValidator.class);
        when(factoryMock.getValidator(any(BiometricTypes.class))).thenReturn(validator);
        data1 = new BiometricData(BiometricTypes.CODE, new byte[]{1, 2, 3});
        data2 = new BiometricData(BiometricTypes.FACE, new byte[]{1, 2, 3});
        successU1 = ValidationResponse.successfulValidation("user1");
        successU2 = ValidationResponse.successfulValidation("user2");
        fail = ValidationResponse.failedValidation("Fail.");
    }

    @Test
    public void testRequestAccess() throws Exception {

        ArrayList<BiometricData> l = new ArrayList<>();
        AccessRequest requestIn = new AccessRequest("1", DoorActions.IN,l);
        AccessRequest requestOut = new AccessRequest("2", DoorActions.OUT,l);
        testAccessEmptyRequest(requestIn);
        testAccessEmptyRequest(requestOut);


        l.add(data1);
        testAccessOneItemList(requestIn);
        testAccessOneItemList(requestOut);

        l.add(data2);
        testAccessMultipleItemList(requestIn);
        testAccessMultipleItemList(requestOut);


    }
    /***
     * Test AccessRequest with two items in list
     * One denied: No Access
     * Different users: No Access
     * All approved & same u: Access
     */
    private void testAccessMultipleItemList(AccessRequest request) throws BiometricTypeException {
        DoorActions action = request.getAction();

        when(validator.validate(data1, action)).thenReturn(successU1);
        when(validator.validate(data2, action)).thenReturn(successU2);
        AccessResponse resp = testee.requestAccess(request);
        assertFalse(resp.getResult());

        when(validator.validate(data2, action)).thenReturn(fail);
        resp = testee.requestAccess(request);
        assertFalse(resp.getResult());

        when(validator.validate(data2, action)).thenReturn(successU1);
        resp = testee.requestAccess(request);
        assertTrue(resp.getResult());
    }

    /**
     * Test AccessRequest with only one item in list
     * Denied: no access
     * Approved: Access
     */
    private void testAccessOneItemList(AccessRequest request) throws BiometricTypeException {

        ValidationResponse successU1 = ValidationResponse.successfulValidation("user1");
        ValidationResponse fail = ValidationResponse.failedValidation("Failure");


        when(validator.validate(data1, request.getAction())).thenReturn(successU1);
        AccessResponse resp = testee.requestAccess(request);
        assertTrue(resp.getResult());

        when(validator.validate(data1, request.getAction())).thenReturn(fail);
        resp = testee.requestAccess(request);
        assertFalse(resp.getResult());
    }

    /**
     * Test AccessRequest with empty list -- denied.
     */
    private void testAccessEmptyRequest(AccessRequest request) {
        AccessResponse resp = testee.requestAccess(request);
        assertFalse(resp.getResult());
    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testApproveUser() throws Exception {

    }

    @Test
    public void testRemoveUser() throws Exception {

    }
}