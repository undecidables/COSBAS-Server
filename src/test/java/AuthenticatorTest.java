import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import cosbas.authentication.Authenticator;

/**
 * This class is the unit tested for the authentication package. It contains unit tests to make sure successful/unsuccessful authentication happens at the correct places.
 *
 * This test is ignored because:
 *      - it is not a proper unit test
 *      - the authenticatior s not actually being used.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AuthenticatorTest {

    private Authenticator servlet = new Authenticator();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    /**
     * This method sets up the value to be returned if getSession is called - essentially it provides a value for the Mock object.
     */
    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    /**
     * This method is used to test a bad password against a username.
     * Does this by setting up values which should be returned by the request object's params
     * Then creating an authenticator object and doing the authentication against LDAP and returning an HttpSession
     * Should return "authentication failed" - the unit test will fail otherwise.
     */
    @Test
    public void testBadStudentPassword() throws IOException, ServletException
    {
        HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
        HttpSession stubHttpSession = mock(HttpSession.class);

        when(stubHttpServletRequest.getParameter("username")).thenReturn("u89000121");
        when(stubHttpServletRequest.getParameter("password")).thenReturn("caloooo");

        when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);

        StringWriter sw = new StringWriter();
        PrintWriter pw  =new PrintWriter(sw);

        when(stubHttpServletResponse.getWriter()).thenReturn(pw);

        Authenticator sampleServlet =new Authenticator();
        sampleServlet.doProcess(stubHttpServletRequest, stubHttpServletResponse);
        String result = sw.getBuffer().toString().trim();
        TestCase.assertEquals(result, new String("Authentication Failed"));


    }
    /**
     * This method is used to test a good password against a username.
     * Does this by setting up values which should be returned by the request object's params
     * Then creating an authenticator object and doing the authentication against LDAP and returning an HttpSession
     * Should return "username + password + authenticated" - the unit test will fail otherwise.
     */
    @Test
    public void testGoodStudentPassword() throws IOException, ServletException
    {
        HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
        HttpSession stubHttpSession = mock(HttpSession.class);

        when(stubHttpServletRequest.getParameter("username")).thenReturn("u89000121");
        when(stubHttpServletRequest.getParameter("password")).thenReturn("Green");

        when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);

        StringWriter sw = new StringWriter();
        PrintWriter pw  =new PrintWriter(sw);

        when(stubHttpServletResponse.getWriter()).thenReturn(pw);

        Authenticator sampleServlet =new Authenticator();
        sampleServlet.doProcess(stubHttpServletRequest, stubHttpServletResponse);
        String result = sw.getBuffer().toString().trim();
        TestCase.assertEquals(result, new String("u89000121 Green authenticated"));
    }
    /**
     * This method is used to test a good password against a staff username.
     * Does this by setting up values which should be returned by the request object's params
     * Then creating an authenticator object and doing the authentication against LDAP and returning an HttpSession
     * Should return "username + password + authenticated" - the unit test will fail otherwise.
     */
    @Test
    public void testGoodStaffPassword() throws IOException, ServletException
    {
        HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
        HttpSession stubHttpSession = mock(HttpSession.class);

        when(stubHttpServletRequest.getParameter("username")).thenReturn("BCrawley");
        when(stubHttpServletRequest.getParameter("password")).thenReturn("Crawley");

        when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);

        StringWriter sw = new StringWriter();
        PrintWriter pw  =new PrintWriter(sw);

        when(stubHttpServletResponse.getWriter()).thenReturn(pw);

        Authenticator sampleServlet =new Authenticator();
        sampleServlet.doProcess(stubHttpServletRequest, stubHttpServletResponse);
        String result = sw.getBuffer().toString().trim();
        TestCase.assertEquals(result, new String("BCrawley Crawley authenticated"));
    }
    /**
     * This method is used to test a bad password against a staff username.
     * Does this by setting up values which should be returned by the request object's params
     * Then creating an authenticator object and doing the authentication against LDAP and returning an HttpSession
     * Should return "username + password + authenticated" - the unit test will fail otherwise.
     */
    @Test
    public void testBadStaffPassword() throws IOException, ServletException
    {
        HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
        HttpSession stubHttpSession = mock(HttpSession.class);

        when(stubHttpServletRequest.getParameter("username")).thenReturn("BCrawley");
        when(stubHttpServletRequest.getParameter("password")).thenReturn("Green");

        when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);

        StringWriter sw = new StringWriter();
        PrintWriter pw  =new PrintWriter(sw);

        when(stubHttpServletResponse.getWriter()).thenReturn(pw);

        Authenticator sampleServlet =new Authenticator();
        sampleServlet.doProcess(stubHttpServletRequest, stubHttpServletResponse);
        String result = sw.getBuffer().toString().trim();
        TestCase.assertEquals(result, new String("Authentication Failed"));
    }
}
