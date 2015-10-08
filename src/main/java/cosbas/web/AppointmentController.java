/**
 * @author Renette
 * MVC Controller for appointment System.
 * This conmtroller serves Thymeleaf HTML templates - so Mostly for GET Requests
 */

package cosbas.web;


import cosbas.calendar_services.*;
import cosbas.calendar_services.authorization.Authorizer;
import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.CredentialWrapper;
import cosbas.calendar_services.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AppointmentController {

    private CalendarDBAdapter credentialRepository;
    private CalendarFactory calendarServiceFactory;

    @Autowired
    private GoogleCalendarService test;

    @Autowired
    public void setCredentialRepository(CalendarDBAdapter credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Autowired
    public void setCalendarServiceFactory(CalendarFactory calendarServiceFactory) {
        this.calendarServiceFactory = calendarServiceFactory;
    }

    /**
     * Route function to go to index.html - Homepage for the user after login
     *
     * @return index.html page
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * Route function to go to index.html - Homepage for the user or makeAppointment.html depending on if user is logged in or not
     *
     * @return index.html page - if logged in or makeAppointment.html - if not logged in
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Principal principal) {
        return principal == null ? "redirect:/makeAppointment" : "index";
    }

    /**
     * Route function to go to login.html - Login page for staffmembers to login on
     *
     * @return login.html page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
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
     *
     * @return makeAppointment.html page
     */
    @RequestMapping(value = "/makeAppointment", method = RequestMethod.GET)
    public String makeAppointment() {
        return "makeAppointment";
    }

    /**
     * Route function to go to logout.html - the user is then logged out
     *
     * @return logout.html page
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }

    /**
     * Route function to go to addUser.html - Page where a new user can be added to the system
     *
     * @return addUser.html page
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser() {
        return "addUser";
    }

    /**
     * Route function to go to status.html - Page where a user can check the status of their appointments
     *
     * @return status.html page
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String status() {
        return "status";
    }

    /**
     * Route function to go to cancel.html - Page where a user can cancel their appointments
     *
     * @return cancel.html page
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancel() {
        return "cancel";
    }

    /**
     * Route function to go to approveDenyAppointment.html - Page where a staff member can approve or deny requested appointments
     *
     * @return approveDenyAppointment.html page
     */
    @RequestMapping(value = "/approveDenyAppointment", method = RequestMethod.GET)
    public String approveDenyAppointment() {
        return "approveDenyAppointment";
    }


    /**
     * Route function to go to adderror.html - Page showing the user what error occured
     *
     * @return error.html page
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError() {
        return "error";
    }


    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView method(HttpSession session, @RequestParam(value = "type", required = false, defaultValue = "GOOGLE") String type) {
        try {
            CalendarType t = CalendarType.valueOf(type);
            Authorizer auth = calendarServiceFactory.getAuthorizer(t);
            auth.startFlow();
            session.setAttribute("authorizer", auth);
            return new ModelAndView("redirect:" + auth.buildLoginUrl());
        } catch (IllegalArgumentException e) {
            return new ModelAndView(("redirect:/error"));
        }
    }

    /**
     * Callback for Google Authentication. Can also be used for other Oauth Autneticators with similiar setups.
     *
     * @param p Principal object from session that contains login details.
     * @param code Authorization code to create credential from.
     * @return Either index or login page.
     * */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String saveCode(Principal p, HttpSession session,
                           @RequestParam(value = "code", required = false, defaultValue = "") String code) {
        if (p == null || code.isEmpty()) {
            return "login";
        } else {
            Authorizer auth = (Authorizer) session.getAttribute("authorizer");
            session.removeAttribute("authorizer");
            CredentialWrapper credential = auth.getCredential(p.getName(), code);
            credentialRepository.save(credential);
        }

        return "index";
    }
}