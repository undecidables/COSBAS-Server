package cosbas.user;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@author Renette}
 */
public class ContactDetailTest {
    ContactDetail testee1 = new ContactDetail(ContactTypes.EMAIL, "a@b.c");
    ContactDetail testee2 = new ContactDetail(ContactTypes.EMAIL, "a@b.c");
    ContactDetail testee3 = new ContactDetail(ContactTypes.EMAIL, "a@b.cd");
    Object testee4 = new Object();


    @Test
    public void testEquals() throws Exception {
        assertTrue(testee1.equals(testee2));
        assertFalse(testee1.equals(null));
        assertFalse(testee1.equals(testee3));
        assertFalse(testee1.equals(testee4));

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(testee1.hashCode(), testee2.hashCode());
    }
}