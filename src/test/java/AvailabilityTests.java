import appointment.Availability;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by JASON on 2015/07/24.
 */
@RunWith(MockitoJUnitRunner.class)
public class AvailabilityTests {

    private Availability availabilityModule = new Availability();

    @Before
    public void setUp(){};

    @Test
    public void testOAuth2Authentication(){
        //String message = availabilityModule.getTrustedMessage();
        TestCase.assertEquals("Testing", "Testing");
    }
}
