/**
 * @author Renette
 * MVC Controller for appointment System.
 * This conmtroller serves Thymeleaf HTML templates - so Mostly for GET Requests
 */

package cosbas.web;

//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.*;
//import org.springframework.security.authentication.*;
//import org.springframework.web.servlet.ModelAndView;

import cosbas.calendar_services.CalendarType;
import cosbas.calendar_services.GoogleAuthorization;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

@Controller
public class AppointmentController {
    GoogleAuthorization Auth;
  /**
  * Route function to go to index.html - Homepage for the user after login
  * @return index.html page
  */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index()
  {
    return "index";
  }

  /**
  * Route function to go to index.html - Homepage for the user or makeAppointment.html depending on if user is logged in or not
  * @return index.html page - if logged in or makeAppointment.html - if not logged in
  */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String root(Principal principal)
  {
    return principal == null ? "redirect:/makeAppointment" : "index";
    //return "index";
  }

  /**
  * Route function to go to login.html - Login page for staffmembers to login on
  * @return login.html page
  */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Principal principal)
  {
    /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if(!(auth instanceof AnonymousAuthenticationToken)){
      return "index";//new ModelAndView("forward:/index");
    } else { 
      return "login";
    }*/
    return principal == null ? "login" : "index";
  }

  /**
  * Route function to go to makeAppointment.html - Page for users to request an appointment with a staffmember
  * @return makeAppointment.html page
  */
  @RequestMapping(value = "/makeAppointment", method = RequestMethod.GET)
  public String makeAppointment()
  {
    return "makeAppointment";
  }

  /**
  * Route function to go to logout.html - the user is then logged out
  * @return logout.html page
  */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout()
  {
    return "logout";
  }

  /**
  * Route function to go to addUser.html - Page where a new user can be added to the system
  * @return addUser.html page
  */
  @RequestMapping(value = "/addUser", method = RequestMethod.GET)
  public String addUser()
  {
    return "addUser";
  }

 /**
  * Route function to go to status.html - Page where a user can check the status of their appointments
  * @return status.html page
  */
  @RequestMapping(value = "/status", method = RequestMethod.GET)
  public String status()
  {
    return "status";
  }

   /**
  * Route function to go to cancel.html - Page where a user can cancel their appointments
  * @return cancel.html page
  */
  @RequestMapping(value = "/cancel", method = RequestMethod.GET)
  public String cancel()
  {
    return "cancel";
  }

 /**
  * Route function to go to approveDenyAppointment.html - Page where a staff member can approve or deny requested appointments
  * @return approveDenyAppointment.html page
  */
  @RequestMapping(value = "/approveDenyAppointment", method = RequestMethod.GET)
  public String approveDenyAppointment()
  {
    return "approveDenyAppointment";
  }


 /**
  * Route function to go to adderror.html - Page showing the user what error occured
  * @return error.html page
  */
  @RequestMapping(value = "/error", method = RequestMethod.GET)
  public String handleError()
  {
    return "error";
  }


    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView method(@RequestParam(value = "type", required = false, defaultValue = "GOOGLE") String type) {
        Auth = new GoogleAuthorization();
        return new ModelAndView("redirect:" + Auth.buildLoginUrl());
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String saveCode(Principal p,
            @RequestParam(value = "code", required = false, defaultValue = "") String code){
        try {
            Auth.getUserInfoJson(code);
            if (p == null){
                System.out.println("Principle is null, could not store code.");
            }
            else {
                Auth.storeCredential(p.getName(), code);
            }
        }
        catch (IOException error){
            System.out.println("Error getting JSON objects");
            return "login";
        }
        return "index";
    }
}