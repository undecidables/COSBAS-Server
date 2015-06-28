package authentication;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class Authenticator extends HttpServlet {
    public Authenticator() {

    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String usr = request.getParameter("username");
            String pwd = request.getParameter("password");
            User user = new User(usr, pwd);
            LDAPTester a = new LDAPTester();
            if (a.authenticateLDAP(usr, pwd)) {
                login(request, response, user);
            }
            else
            {
                PrintWriter out = response.getWriter();
                out.println("Authentication Failed");
            }
        }
        catch(Exception e)
        {

        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

        String Username = user.getUserID();
        String Password = user.getPassword();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        session.setAttribute("username", Username);
        session.setAttribute("password", Password);
        out.println(Username + " " + Password + " " + "authenticated");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

