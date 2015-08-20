package cosbas.authentication;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is responsible for authenticating a user. It does this by getting the username and password from the HttpServletRequest object and then sending those through the LDAP
 * authenticator.
 */
public class Authenticator extends HttpServlet {

    /**
     * This method is responsible for doing the actual authentication - when a user is authenticated using LDAP, they are logged in to the system.
     * It is done by getting the username and password from the request object, then constructing a user object from those variables.
     * The username and password are then validated against ldap - if authentication succeeds, it logs the user in by creating and returning an HttpSession
     * Otherwise it returns null
     * @param request This is the HttpServletRequest (an http request object) which will contain the command sent from the browser
     * @param response An http response object
     * @return HttpSession This is the session that the user will be using after they've been logged in
     */
    public HttpSession doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String usr = request.getParameter("username");
            String pwd = request.getParameter("password");
            User user = new User(usr, pwd);
            LDAPTester a = new LDAPTester();
            if (a.authenticateLDAP(usr, pwd)) {
                HttpSession session = login(request, response, user);
                return session;
            }
            else
            {
                PrintWriter out = response.getWriter();
                out.println("Authentication Failed");
                return null;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * This method is responsible for logging a user in by creating a session from the user's details.
     * @param request This is the HttpServletRequest (an http request object) which will contain the command sent from the browser
     * @param response An http response object
     * @return HttpSession This is the session that the user will be using after they've been logged in
     */
    public HttpSession login(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

        String Username = user.getUserName();
        String Password = user.getPassword();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        session.setAttribute("username", Username);
        session.setAttribute("password", Password);
        out.println(Username + " " + Password + " " + "authenticated");
        return session;
    }
}

