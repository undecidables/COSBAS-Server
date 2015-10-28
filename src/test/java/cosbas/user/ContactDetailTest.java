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


    @Test
    public void testContactUpdate()
    {
        User user = new User("test");
        ContactDetail contact1 = new ContactDetail(ContactTypes.EMAIL, "test@gmail.com");
        user.addContactDetail(contact1);
        ContactDetail contact2 = new ContactDetail(ContactTypes.EMAIL, "new@gmail.com");
        assertNotEquals(user.getContact().get(0).getDetails(), contact2.getDetails());
        user.updateContactDetail(ContactTypes.EMAIL, contact2);
        assertEquals(user.getContact().get(0).getDetails(), contact2.getDetails());
        assertNotEquals(user.getContact().get(0).getDetails(), contact1.getDetails());
    }
}