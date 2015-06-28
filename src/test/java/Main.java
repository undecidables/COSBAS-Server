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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import authentication.Authenticator;

@RunWith(MockitoJUnitRunner.class)
public class Main {

    private Authenticator servlet = new Authenticator();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

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
    @Test
    public void testValidStaffUsername() throws IOException, ServletException
    {
        HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
        HttpSession stubHttpSession = mock(HttpSession.class);

        when(stubHttpServletRequest.getParameter("username")).thenReturn("BCrawley1");
        when(stubHttpServletRequest.getParameter("password")).thenReturn("Crawley");

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
