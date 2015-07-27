import appointment.Availability;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

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
        try {
            boolean authorized = availabilityModule.doAuthorize();
            TestCase.assertEquals("Could not authenticate via oAuth2", true, authorized);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
