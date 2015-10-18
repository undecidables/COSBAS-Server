/*
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import cosbas.appointment.Appointments;

*/
/**
 * This class is the unit tested for the authentication package. It contains unit tests to make sure successful/unsuccessful authentication happens at the correct places.
 *//*

@RunWith(MockitoJUnitRunner.class)
public class AppointmentTest {
/
    private final Appointments mockAppointments;
    private final AppointmentDBAdapter mockRepository;
    private final String appointmentID;
    private final String tempAppointment;

    */
/**
     * This method is used to select the calander to be used as well as the available times for appointments
     *//*

    @Before
    public void setUp() throws Exception {
        mockAppointments = new Appointments();
        mockRepository = mock(AppointmentDBAdapter.class);
        mockAppointments.setRepository(mockRepository);
    }

    */
/*
    Tests to do
    requestAppointment
        savable appointment
        unsavable appointment
        multiple users requesting same time
    *//*


    */
/**
     * This method tests a single user requesting an appointment
     * Should not return "Could not save appointment." - the unit test will fail otherwise.
     *//*

    @Test
    public void testSavedAppointment() throws IOException
    {
        List<String> visitors = new List<String>();
        visitors.add("Person1");
        LocalDateTime date = new LocalDateTime();
        String result = mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);
        appointmentID = result.subStrin(13, (result.length - 16));
        TestCase.assertNotEqual()(result, new String("Could not save appointment."));
    }

     */
/**
     * This method tests appointment with errors
     * It tests that no two appointments can be made on the same date
     * Should return "Could not save appointment." - the unit test will fail otherwise.
     *//*

    @Test
    public void testUnSavableAppointment() throws IOException
    {
        List<String> visitors = new List<String>();
        visitors.add("Person1");
        LocalDateTime date = new LocalDateTime();
        mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);
        String result = mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);
        TestCase.assertEquals(result, new String("Could not save appointment."));
    }
    

     */
/**
     * This method tests multiple user requesting the same appointment time
     * Should return "Could not save appointment." - the unit test will fail otherwise.
     *//*

    @Test
    public void testMultipleUsersSameTimeAppointment() throws IOException
    {
        List<String> visitors = new List<String>();
        visitors.add("Person1");
        LocalDateTime date = new LocalDateTime();
        String result = mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);

        TestCase.assertEquals(result, new String("Could not save appointment."));
    }
    
    */
/*
    Tests to do
    cancelAppointment
        non existent appointment
        unauthorized appointment
        cancelling appointment
        already cancelled
    *//*


    */
/**
     * This method tests cancelling an non-existing appointment
     * Should return "Appointment does not exist.6" - the unit test will fail otherwise.
     *//*

    @Test
    public void testCancellingNonExistingAppointment() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person1", "123456789");

        TestCase.assertEquals(result, new String("Appointment does not exist."));
    }

    */
/**
     * This method tests a unauthorized user cancelling an appointment
     * Should return "You are not authorised to cancel this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testUnaurtherizedUserCancelling() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person2", appointmentID);

        TestCase.assertEquals(result, new String("You are not authorised to cancel this appointment"));
    }

    */
/**
     * This method tests cancelling an appointment
     * Should return "Appointment has been cancelled." - the unit test will fail otherwise.
     *//*

    @Test
    public void testUserCancelling() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person1", appointmentID);

        TestCase.assertEquals(result, new String("Appointment has been cancelled."));
    }


    */
/**
     * This method tests cancelling an appointment that was already cancelled
     * Should return "Appointment has already been cancelled." - the unit test will fail otherwise.
     *//*

    @Test
    public void testCancellingAlreadyCancelledAppointment() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person1", appointmentID);

        TestCase.assertEquals(result, new String("Appointment has already been cancelled."));
    }

    */
/*
    Tests to do
    checkStatus
        single member wrong appointments
        single member wrong person
        single member right person 
    *//*


    */
/**
     * This method tests checking an appointment's status for a non-existing appointment
     * Should return "No such Appointment exists" - the unit test will fail otherwise.
     *//*

    @Test
    public void testCheckStatusNonExistingAppointment() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person1", "123456789");

        TestCase.assertEquals(result, new String("No such Appointment exists"));
    }

    */
/**
     * This method tests checking an appointment's status by an unauthorized user
     * Should return "You are not authorised to view this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testCheckStatusUnauthorizedUser() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person2", appointmentID);

        TestCase.assertEquals(result, new String("You are not authorised to view this appointment"));
    }

    */
/**
     * This method tests checking an appointment's status
     * Should not return "You are not authorised to view this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testCheckStatus() throws IOException
    {
        String result = mockAppointments.cancelAppointment("Person1", appointmentID);

        TestCase.assertNotEqual(result, new String("You are not authorised to view this appointment"));
    }

    */
/*
    Tests to do
    approveAppointment
        wrong appointment 
        correct appointment not requested
        correct appointment
    *//*


    */
/**
     * This method tests the approval of an appointment by an unathorized user
     * Should return "You are not authorised to accept this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testApproveAppointmentUnauthorizedUser() throws IOException
    {
        String result = mockAppointments.approveAppointment(appointmentID, "Person1");

        TestCase.assertEqual(result, new String("You are not authorised to accept this appointment"));
    }

    */
/**
     * This method tests the approval of an appointment that's status isn't "requested"
     * Should not return "You are not authorised to accept this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testApproveAppointmentAppointmentNotRequested() throws IOException
    {
        String result = mockAppointments.approveAppointment(appointmentID, "Proff Goodman");

        TestCase.assertNotEqual(result, new String("You are not authorised to accept this appointment"));
    }

    */
/**
     * This method tests the approval of an appointment
     * Should not return "Appointment approved" - the unit test will fail otherwise.
     *//*

    @Test
    public void testApproveAppointment() throws IOException
    {
        List<String> visitors = new List<String>();
        visitors.add("Person3");
        LocalDateTime date = new LocalDateTime();
        String result = mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);
        tempAppointment = result.subStrin(13, (result.length - 16));

        String result = mockAppointments.approveAppointment(tempAppointment, "Proff Goodman");

        TestCase.assertNotEqual(result, new String("Appointment approved"));
    }

    */
/**
     * This method tests the approval of an non-existing appointment
     * Should not return "No such Appointment exists" - the unit test will fail otherwise.
     *//*

    @Test
    public void testApproveAppointmentNonExisting() throws IOException
    {
        String result = mockAppointments.approveAppointment("123456789", "Proff Goodman");

        TestCase.assertNotEqual(result, new String("No such Appointment exists"));
    }

    */
/*
    Tests to do
    denyAppointment
        wrong appointment 
        correct appointment not requested
        correct appointment
    *//*


    */
/**
     * This method tests the denial of an appointment by an unathorized user
     * Should return "You are not authorised to cancel this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testDenyAppointmentUnauthorizedUser() throws IOException
    {
        String result = mockAppointments.denyAppointment(appointmentID, "Person1");

        TestCase.assertEqual(result, new String("You are not authorised to cancel this appointment"));
    }

    */
/**
     * This method tests the denial of an appointment that that's status isn't "requested"
     * Should not return "You are not authorised to deny this appointment" - the unit test will fail otherwise.
     *//*

    @Test
    public void testDenyAppointmentAppointmentNotRequested() throws IOException
    {
        String result = mockAppointments.denyAppointment(appointmentID, "Person1");

        TestCase.assertNotEqual(result, new String("You are not authorised to deny this appointment"));
    }

    */
/**
     * This method tests the denial of an appointment
     * Should return "Appointment denied" - the unit test will fail otherwise.
     *//*

    @Test
    public void testDenyAppointment() throws IOException
    {
        List<String> visitors = new List<String>();
        visitors.add("Person4");
        LocalDateTime date = new LocalDateTime();
        String result = mockAppointments.requestAppointment(visitors, "Proff Goodman", date, "Meeting", 45);
        tempAppointment = result.subStrin(13, (result.length - 16));

        String result = mockAppointments.denyAppointment(tempAppointment, "Person1");

        TestCase.assertEqual(result, new String("Appointment denied"));
    }

    */
/**
     * This method tests the denial of an non-existing appointment
     * Should return "No such Appointment exists" - the unit test will fail otherwise.
     *//*

    @Test
    public void testDenyAppointmentNonExisting() throws IOException
    {
        String result = mockAppointments.denyAppointment("123456789", "Person1");

        TestCase.assertEqual(result, new String("No such Appointment exists"));
    }
    *//*

}
*/
