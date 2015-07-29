import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import appointments.Appointments;

/**
 * This class is the unit tested for the authentication package. It contains unit tests to make sure successful/unsuccessful authentication happens at the correct places.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppointmentTests {

    //Mock Calendar
    //Mock availability

    private final Appointments appointments;

    /**
     * This method is used to select the calander to be used as well as the available times for appointments
     */
    @Before
    public void setUpCalendarAvaliabiltiy() {
        
    }

    /*
    Tests to do
    requestAppointment
        one username
        multiple users
        multiple users requesting same time
        staff member
    */

    /**
     * This method tests a single user requesting an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testOneUserAppointment() throws IOException
    {
       
    }

     /**
     * This method tests multiple user requesting an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testMultipleUsersAppointment() throws IOException
    {
       
    }

     /**
     * This method tests multiple user requesting the same appointment time
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testMultipleUsersSameTimeAppointment() throws IOException
    {
       
    }

    /**
     * This method tests a staff member requesting an Appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testStaffMemberAppointment() throws IOException
    {
       
    }
    
    /*
    Tests to do
    cancelAppointment
        single member wrong appointments
        single member wrong person
        single member right person 
        other in the list member right person
        staff member right appointment 
        staff member wrong person
        already cancelled appointment
    */

    /**
     * This method tests cancelling an non-existing appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCancellingNonExistingAppointment() throws IOException
    {
       
    }

    /**
     * This method tests a unauthorized user cancelling an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testUnaurtherizedUserCancelling() throws IOException
    {
       
    }

    /**
     * This method tests cancelling an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testUserCancelling() throws IOException
    {
       
    }

    /**
     * This method tests a user part of a group cancelling an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testUserInAListCancelling() throws IOException
    {
       
    }

    /**
     * This method tests an unauthorized staff member cancelling an Appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testUnauthorizedStaffMemberCancelling() throws IOException
    {
       
    }

    /**
     * This method tests a staff member cancelling an Appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testStaffMemberCancelling() throws IOException
    {
       
    }

    /**
     * This method tests cancelling an appointment that was already cancelled
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCancellingAlreadyCancelledAppointment() throws IOException
    {
       
    }

    /*
    Tests to do
    checkStatus
        single member wrong appointments
        single member wrong person
        single member right person 
        other in the list member right person
        staff member right appointment 
        staff member wrong person
    */

    /**
     * This method tests checking an appointment's status for a non-existing appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatusNonExistingAppointment() throws IOException
    {
       
    }

    /**
     * This method tests checking an appointment's status by an unauthorized user
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatusUnauthorizedUser() throws IOException
    {
       
    }

    /**
     * This method tests checking an appointment's status
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatus() throws IOException
    {
       
    }

    /**
     *  This method tests checking an appointment's status by a user part of a group
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatusUserInAList() throws IOException
    {
       
    }

    /**
     * This method tests checking an appointment's status by an unauthorized staff member
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatusStaffMemberUnauthorizedUser() throws IOException
    {
       
    }

    /**
     * This method tests checking an appointment's status by an authorized staff member
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testCheckStatusStaffMember() throws IOException
    {
       
    }

    /*
    Tests to do
    approveAppointment
        wrong appointment 
        correct appointment not requested
        correct appointment
    */

    /**
     * This method tests the approval of an appointment by an unathorized user
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testApproveAppointmentUnauthorizedUser() throws IOException
    {
       
    }

    /**
     * This method tests the approval of an appointment that's status isn't "requested"
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testApproveAppointmentAppointmentNotRequested() throws IOException
    {
       
    }

    /**
     * This method tests the approval of an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testApproveAppointment() throws IOException
    {
       
    }

    /**
     * This method tests the approval of an non-existing appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testApproveAppointmentNonExisting() throws IOException
    {
       
    }

    /*
    Tests to do
    denyAppointment
        wrong appointment 
        correct appointment not requested
        correct appointment
    */

    /**
     * This method tests the denial of an appointment by an unathorized user
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testDenyAppointmentUnauthorizedUser() throws IOException
    {
       
    }

    /**
     * This method tests the denial of an appointment that that's status isn't "requested"
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testDenyAppointmentAppointmentNotRequested() throws IOException
    {
       
    }

    /**
     * This method tests the denial of an appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testDenyAppointment() throws IOException
    {
       
    }

    /**
     * This method tests the denial of an non-existing appointment
     *              what does it do and return and when sucessfull?
     */
    @Test
    public void testDenyAppointmentNonExisting() throws IOException
    {
       
    }

    //Add aditional tests for calendar and availability testing
    
}
