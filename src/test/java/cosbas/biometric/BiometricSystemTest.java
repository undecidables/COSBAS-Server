package cosbas.biometric;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.ValidatorFactory;
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

    @Before
    public void setUp() throws Exception {
        testee = new BiometricSystem();
        factoryMock = mock(ValidatorFactory.class);
        personRepoMock = mock(BiometricDataDAO.class);
        testee.setFactory(factoryMock);
        testee.setBiometricDataRepository(personRepoMock);
    }

    @Test
    public void testRequestAccess() throws Exception {

        AccessValidator validator = mock(AccessValidator.class);
        when(factoryMock.getValidator(any(BiometricTypes.class))).thenReturn(validator);
        ArrayList<BiometricData> l = new ArrayList<>(2);
        l.add(new BiometricData(BiometricTypes.CODE, new byte[] {1,2,3}));
        l.add(new BiometricData(BiometricTypes.FACE, new byte[] {1,2,3}));


        ValidationResponse successU1 = ValidationResponse.successfulValidation("user1");
        ValidationResponse successU2 = ValidationResponse.successfulValidation("user2");
        ValidationResponse fail = ValidationResponse.failedValidation("Failure");



        /***
         * One denied: No Access
         * Different users: No Access
         * All approved & same u: Access
         */
        AccessRequest request = new AccessRequest("1", DoorActions.IN,l);


        when(validator.validate(l.get(0), DoorActions.IN)).thenReturn(successU1);
        when(validator.validate(l.get(1), DoorActions.IN)).thenReturn(successU2);
        AccessResponse resp = testee.requestAccess(request);
        assertFalse(resp.getResult());

        when(validator.validate(l.get(1), DoorActions.IN)).thenReturn(fail);
        resp = testee.requestAccess(request);
        assertFalse(resp.getResult());

        when(validator.validate(l.get(1), DoorActions.IN)).thenReturn(successU1);
        resp = testee.requestAccess(request);
        assertTrue(resp.getResult());

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