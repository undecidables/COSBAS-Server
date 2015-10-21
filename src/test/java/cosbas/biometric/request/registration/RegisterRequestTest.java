package cosbas.biometric.request.registration;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * {@author Renette Ros}
 */
public class RegisterRequestTest {

    @Test
    public void testMerge() throws Exception {
        ContactDetail e1 = new ContactDetail(ContactTypes.EMAIL, "a@b.c");
        ContactDetail e2 = new ContactDetail(ContactTypes.EMAIL, "a@b.c");
        ContactDetail e3 = new ContactDetail(ContactTypes.EMAIL, "a@b.cd");
        BiometricData data = new BiometricData(BiometricTypes.FACE, new byte[]{1, 2, 3, 4});
        BiometricData data2 = new BiometricData(BiometricTypes.FACE, new byte[]{1, 2, 3, 4, 5, 6});
        BiometricData data3 = new BiometricData(BiometricTypes.FACE, new byte[]{4, 5, 6});
        RegisterRequest testee = new RegisterRequest(new LinkedList<>(Collections.singletonList(e1)), "user1", new LinkedList<>(Collections.singletonList(data)), "");
        RegisterRequest mergeReq = new RegisterRequest(new LinkedList<>(Collections.singletonList(e2)), "user1", new LinkedList<>(Collections.singletonList(data2)), "");

        testee.merge(mergeReq);
        assertEquals(testee.getContactDetails().size(), 1);
        assertEquals(testee.getData().size(), 2);

        mergeReq = new RegisterRequest(new LinkedList<>(Collections.singletonList(e3)), "user1", new LinkedList<>(Collections.singletonList(data3)), "");
        testee.merge(mergeReq);
        assertEquals(testee.getContactDetails().size(), 2);
        assertEquals(testee.getData().size(), 3);


    }
}