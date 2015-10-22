package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterRequestDAO;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Renette
 */
public class BiometricSystemTest {

    private BiometricSystem testee;
    private AccessValidator validator;
    private static BiometricData data1;
    private static BiometricData data2;
    private static ValidationResponse successU1;
    private static ValidationResponse successU2;
    private static ValidationResponse fail;

    @BeforeClass
    public static void globalSetUp() {
        data1 = new BiometricData(BiometricTypes.CODE, new byte[]{1, 2, 3});
        data2 = new BiometricData(BiometricTypes.FACE, new byte[]{1, 2, 3});
        successU1 = ValidationResponse.successfulValidation("user1");
        successU2 = ValidationResponse.successfulValidation("user2");
        fail = ValidationResponse.failedValidation("Fail.");
    }

    @Before
    public void setUp() throws Exception {

        BiometricDataDAO personRepoMock = mock(BiometricDataDAO.class);
        ValidatorFactory factoryMock = mock(ValidatorFactory.class);

        validator = mock(AccessValidator.class);
        when(factoryMock.getValidator(any(BiometricTypes.class))).thenReturn(validator);

        testee = new BiometricSystem();
        testee.setFactory(factoryMock);
        testee.setBiometricDataRepository(personRepoMock);
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
    public void testApproveUser() throws Exception {

    }

    @Test
    public void testRemoveUser() throws Exception {

    }

    @Test
    public void testRegister() throws Exception {
        /**
         * Not testing that merge is called
         * Test save is called
         * test null/empty failure and not saved.
         */
        RegisterRequestDAO repo = mock(RegisterRequestDAO.class);
        testee.setRegisterRepository(repo);

        RegisterRequest req = null;
        RegisterResponse resp = testee.register(req);
        assertFalse(resp.getResult());

        req = new RegisterRequest(new LinkedList<>(), "user1", new LinkedList<>(), "");
        resp = testee.register(req);
        assertFalse(resp.getResult());
        verify(repo, never()).save(req);

        req = new RegisterRequest(Collections.singletonList(new ContactDetail(ContactTypes.EMAIL, "A@b.c")), "user1", Collections.singletonList(new BiometricData("user1", BiometricTypes.FACE, new byte[] {1, 2, 3, 4, 5})), "");
        RegisterRequest repoUser = new RegisterRequest(new LinkedList<>(), "user1", new LinkedList<>(), "");
        when(repo.findByUserID(anyString())).thenReturn(repoUser);
        resp = testee.register(req);
        verify(repo, atLeastOnce()).save(any(RegisterRequest.class));

        assertTrue(resp.getResult());

    }
}