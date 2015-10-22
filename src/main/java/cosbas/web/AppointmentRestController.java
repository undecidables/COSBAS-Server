/**
 * @author Renette
 * Secondary controller for appointment System.
 * This controller serves plain text. It is used for any URL'S that respond to Ajax requests with JSON.
 */

package cosbas.web;

import com.google.common.base.Joiner;
import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import cosbas.appointment.Appointments;
import cosbas.biometric.BiometricSystem;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.calendar_services.authorization.CalendarDBAdapter;
import cosbas.calendar_services.authorization.CredentialWrapper;
import cosbas.calendar_services.services.GoogleCalendarService;
import cosbas.permissions.Permission;
import cosbas.permissions.PermissionId;
import cosbas.permissions.PermissionManager;
import cosbas.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class AppointmentRestController {
    
    //Private variable used to call the appointment class functions of the appointment class
    @Autowired
    private Appointments appointment;

    //The database adapter/repository to use for appointments
    @Autowired
    private AppointmentDBAdapter repository;

    //The database adapter/repository to use for the crendentials
    @Autowired
    private CalendarDBAdapter credentialRepository;

    //Private variable used to call the appointment class functions of the calendar Class
    @Autowired
    private GoogleCalendarService calendar;

    //Private variable used to call the appointment class functions of the PermissionsManager class
    @Autowired
    private PermissionManager permissionManager;

    //Private variable used to call the appointment class functions of the biometricSystem class
    @Autowired
    private BiometricSystem biometricSystem;

    public void setCredentialRepository(CalendarDBAdapter credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The repository to be injected.
     */
    public void setRepository(AppointmentDBAdapter repository) {
        this.repository = repository;
    }

    /**
   * Function used to set up the active users of the system that needs to appear in the list of people an appointment can be made with. It is called as soon as the page is loaded
   * @return Returns the list of active users to be placed in the selection element
   */

    @RequestMapping(method = RequestMethod.POST, value = "/getActiveUsers")
    public String getActiveUsers() {
        List<CredentialWrapper> credentials = credentialRepository.findAll();
        String returnPage = "";
        returnPage += "<select class=\"contact_input\" id=\"appointmentData\" name=\"appointmentWith\">";
        for (int i = 0; i < credentials.size(); i++) {

           // System.out.println(credentials.get(i));
            returnPage += "<option>" + credentials.get(i).getStaffID() + "</option>";
        }

        if (credentials.size() == 0) {
            returnPage += "<option>No active users of the system</option>";
        }
        returnPage += "</select>";
        returnPage += "<p class=\"text-left\">" +
                "<br/>" +
                "<button type=\"submit\" id=\"appointmentsubmit\" class=\"btnLightbox btn-common\">Select Staff Member</button>" +
                "</p>";

        return returnPage;
    }

  /**
   * Fuction used to save the appointment that the user has inputted into the html form on the makeAppointment.html page
   * @param appointmentWith - String staff memeber ID as gotten from the html dropdown on the html page
   * @param appointmentDateTime - String Requested date time for the appointment as inputted into the html form it is converted to LocalDateTime in the function
   * @param appointmentBy - String list of members in the group that is making the appointment as inputted on the htm form
   * @param duration - Integer duration of the appointment in minutes as inputted on the html form
   * @param reason - String reason for the appointment being made as indicated on the html form
   * @return the returned string from the requestAppointment function - It can either be an error message or the appointment identifier
   */

  @RequestMapping(method= RequestMethod.POST, value="/requestAppointment")
  public String requestAppointment(
                     @RequestParam(value = "appointmentWith", required = true) String appointmentWith,
                     @RequestParam(value = "requestedDateTime", required = true) String appointmentDateTime,
                     @RequestParam(value = "appointmentBy", required = true) List<String> appointmentBy,
                     @RequestParam(value = "appointmentDuration", required = true) int duration,
                     @RequestParam(value = "appointmentReason", required = true) String reason,
                     @RequestParam(value = "appointmentEmails", required = true) List<String> emails) {

    LocalDateTime dateTime = LocalDateTime.parse(appointmentDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    return appointment.requestAppointment(appointmentBy, appointmentWith, dateTime, reason, duration, emails);
  }

  /**
   * Function used to cancel an appointment via the form on the cancel.html page
   * @param cancellee - String of the name of the person who wants to cancel the appointment.
   * @param appointmentID - String appointmentID, the appointment ID of the appointment that is being cancelled.
   * @return the status of the appointment - whether the appoitnment was canceled or if an error occured
   */

  @RequestMapping(method= RequestMethod.POST, value="/cancelAppointment")
  public String cancelAppointment(
                     @RequestParam(value = "cancellee", required = true) String cancellee,
                     @RequestParam(value = "appointmentID", required = true) String appointmentID) {

    return appointment.cancelAppointment(cancellee, appointmentID);
  }

  /**
   * Function to check the status of the appointment entered on the status.html form
   * @param requester - String name of the person requesting the status of the appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to check
   * @return Returns the status and information of the appointment or an appropriate string describing the error
   */

  @RequestMapping(method= RequestMethod.POST, value="/status")
  public String checkStatus(
                     @RequestParam(value = "requester", required = true) String requester,
                     @RequestParam(value = "appointmentID", required = true) String appointmentID) {

    return appointment.checkStatus(requester, appointmentID);
  }

  /**
   * Function used to set up the requested appointments that need to be approved or denied. It is called as soon as the page is loaded
   * @return Returns the approve or deny options of each relevant appointment
   */

  @RequestMapping(method= RequestMethod.POST, value="/getApproveOrDeny")
  public String getApproveOrDeny(Principal principal) {
      List<Appointment> appointments = repository.findByStatusLike("requested");
      String staffMember = principal.getName();
      String returnPage = "";

      for (int i = 0; i < appointments.size(); i++) {
          String[] parts = appointments.get(i).getDateTime().toString().split("T");
          String tempDateTime = parts[0] + " at " + parts[1].substring(0, parts[1].length() - 3);

          if (appointments.get(i).getStaffID().equals(staffMember)) {
              returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\"><p class='text-left'>Appointment with " + Joiner.on(", ").join(appointments.get(i).getVisitorIDs()) + "</p></td>" +
                      "<td colspan=\"2\"><p>On: " + tempDateTime + "</p></td>" +
                      "<td colspan=\"2\"><p>Duration: " + appointments.get(i).getDurationMinutes() + " minutes</p></td>" +
                      "<td><button type='submit' class='form-control accept approveBtn'><i class=\"fa fa-check-circle\"></i></button></td>" +
                      "<td><button class='form-control deny denyBtn' type='submit' value='Deny'><i class = \"fa fa-times\"></i></button></td></tr>" +
                      "<tr class=\"hiddenData\"><td><input type='text' class='appointmentID' value='" + appointments.get(i).getId() + "' hidden/><input type='text' class='staffID' value='" + staffMember + "' hidden/></td>" +
                      "</tr>" +
                      "</table>";
          }

      }

      if (appointments.size() == 0 || returnPage == "") {
          returnPage += "<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No Appointments</span> Pending</h4>";
      }
      return returnPage;
  }

  /**
   * Function to approve a pending appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to approve
   * @param staffMember - StaffID of the staff member who is approving the appointment. Used to check that it is your appointmnet that you are approving
   * @return Returns the message detailing what happened when trying to approve the appointment as gotten from the approveAppointment method
   */

  @RequestMapping(method= RequestMethod.POST, value="/approve")
   public String approve( @RequestParam(value = "appointmentID", required = true) String appointmentID,
                          @RequestParam(value = "staffMember", required = true) String staffMember) {
    
      return appointment.approveAppointment(appointmentID, staffMember);
   }

   /**
   * Function to deny a pending appointment
   * @param appointmentID - String appoitnment ID of the appointment that you want to deny
   * @param staffMember - StaffID of the staff member who is denying the appointment. Used to check that it is your appointmnet that you are denying
   * @return Returns the message detailing what happened when trying to deny the appointment as gotten from the denyAppointment method
   */

  @RequestMapping(method= RequestMethod.POST, value="/deny")
   public String deny( @RequestParam(value = "appointmentID", required = true) String appointmentID,
                          @RequestParam(value = "staffMember", required = true) String staffMember) {

      return appointment.denyAppointment(appointmentID, staffMember);
   }

 /**
   * Function used to set up the index page with the logged in users appointments. It is called as soon as the page is loaded
  * @return Returns the logged in user's month's appointments
   */

 @RequestMapping(method = RequestMethod.POST, value = "/getMonthAppointments")
 public String getMonthAppointments(Principal principal) {
     Date date = new Date();
     LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
     int month = localDate.getMonthValue();

     List<Appointment> appointments = calendar.getMonthAppointments(principal.getName(), month);

     String returnPage = "[";

      for(int i = 0; i < appointments.size(); i++)
      {
        String[] parts = appointments.get(i).getDateTime().toString().split("T");
          int duration = appointments.get(i).getDurationMinutes();
          String startDate = appointments.get(i).getDateTime().toString();
          String summary = appointments.get(i).getSummary();

          if (i != appointments.size() - 1) {
              returnPage += "{\"title\":\"" + summary + "\", \"withWho\": \"" + Joiner.on(", ").join(appointments.get(i).getVisitorIDs()) + "\", \"start\": \"" + startDate + "\", \"duration\": \"" + duration + "\"},";
          } else {
              returnPage += "{\"title\":\"" + summary + "\", \"withWho\": \"" + Joiner.on(", ").join(appointments.get(i).getVisitorIDs()) + "\", \"start\": \"" + startDate + "\", \"duration\": \"" + duration + "\"}";
          }
      }
     return (returnPage + "]");
  }

  /**
   * Function used to set up the index page with the logged in users appointments. It is called as soon as the page is loaded
   * @return Returns the logged in user's day's appointments
   */

  @RequestMapping(method= RequestMethod.POST, value="/getDayAppointments")
  public String getDayAppointments(Principal principal) {
    List<Appointment> appointments = calendar.getTodaysAppointments(principal.getName());
    
    String returnPage = "";

    if(appointments != null){
      for(int i = 0; i < appointments.size(); i++)
      {
        List<String> with = appointments.get(i).getVisitorIDs();
        int duration = appointments.get(i).getDurationMinutes();
        String reason = appointments.get(i).getReason();
        String[] parts = appointments.get(i).getDateTime().toString().split("T");
        String tempDateTime = parts[1].substring(0, parts[1].length()-3);
        returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\"><p class='text-left'>Appointment with " + Joiner.on(", ").join(appointments.get(i).getVisitorIDs()) + "</p></td>" +
                      "<td colspan=\"2\"><p>At: " + tempDateTime + "</p></td>" +
                      "<td colspan=\"2\"><p>Duration: " + appointments.get(i).getDurationMinutes() + " minutes</p></td>" +
                      "</tr>" +
                      "</table>";
      }
        
      if(appointments.size() == 0){
        returnPage += "<h3 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><span>You have no appointments</span> For Today</h3>";      
      } 
    } else {
      returnPage += "<h3 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><span>You have no appointments</span> For Today</h3>";  
    }
    return returnPage;
  }

  /**
   * Function used to check if the current logged in user has already linked their callendar or not so that a pop up can be given to link a calendar if needed.
   * @return Returns Linked or Not Linked depending on if the calendar was already linked. 
   */

  @RequestMapping(method= RequestMethod.POST, value="/calendarLinked")
  public String getLinkedCalendar(Principal principal) {
    CredentialWrapper credentials = null;
    credentials = credentialRepository.findByStaffID(principal.getName());

    if(credentials == null){
      return "Not Linked";
    } else {
      return "Linked";
    }
  }

  /**
   * Function used to return the times a staff member has appointments if a time clash was requested. 
   * This is done to help the user choose a none conflicting time
   * @return Returns string with the html code to display the times of appointments for the day 
   */

  @RequestMapping(method= RequestMethod.POST, value="/dayAvailable")
    public String getAvailableTimes(@RequestParam(value = "staffID", required = true) String staffID) {
    List<Appointment> appointments = calendar.getTodaysAppointments(staffID);
    
    String returnPage = "";

    if(appointments != null){
      for(int i = 0; i < appointments.size(); i++)
      {
        List<String> with = appointments.get(i).getVisitorIDs();
        int duration = appointments.get(i).getDurationMinutes();
        String reason = appointments.get(i).getReason();
        String[] parts = appointments.get(i).getDateTime().toString().split("T");
        String tempDateTime = parts[1].substring(0, parts[1].length()-3);
        returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\"><p>Appoinment at: " + tempDateTime + "</p></td>" +
                      "<td colspan=\"5\"><p>Duration: " + appointments.get(i).getDurationMinutes() + " minutes</p></td>" +
                      "</tr>" +
                      "</table>";
      }
        
      if(appointments.size() == 0){
        returnPage += "<h3 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><span>You have no appointments</span> For Today</h3>";      
      } 
    } else {
      returnPage += "<h3 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><span>You have no appointments</span> For Today</h3>"; 
    }
    return returnPage;
  }

  /**
   * Function used to return all the users who have requested to be registered on the system
   * @return Returns string with the html code to display the users that have requested to be registered
   */
  @RequestMapping(method= RequestMethod.POST, value="/getRequestedUsersForRegistration")
  public String getRequestedUsersForRegistration() {
    List<RegisterRequest> requests = biometricSystem.getRegisterRequests();
    
    String returnPage = "";

    if(requests != null){
      for(int i = 0; i < requests.size(); i++)
      {
        String[] requestDetails = requests.get(i).toString().split(",");
        String[] requestDateTime = requestDetails[0].split("T");
        returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\"><p>Date: " + requestDateTime[0] + "</p></td>" +
                      "<td colspan=\"2\"><p>Time: " + requestDateTime[1].substring(0, requestDateTime[1].length()-7) + "</p></td>" +
                      "<td colspan=\"2\" class=\"userCol\"><p class=\"userID\">User ID: <span class=\"actualID\">" + requestDetails[1] + "</span></p></td>" +
                      "<td colspan=\"2\"><p>Requested by: " + requestDetails[2] + "</p></td>" +
                      "<td><button type='submit' class='form-control accept approveBtn'><i class=\"fa fa-check-circle\"></i></button></td>" +
                      "<td><button class='form-control deny denyBtn' type='submit' value='Deny'><i class = \"fa fa-times\"></i></button></td></tr>" +
                      "</tr>" +
                      "</table>";
      }
        
      if(requests.size() == 0){
        returnPage += "<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>";      
      } 
    } else {
      returnPage += "<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>";      
    }
    return returnPage;
  }

  /**
  * Function to approve a registration request
  * @Returns feedback from the biometric function
  */
  @RequestMapping(method= RequestMethod.POST, value="/approveRequest")
  public String approveRequest(@RequestParam(value = "staffID", required = true) String staffID) {
    try{
      biometricSystem.approveUser(staffID);
    }
    catch (Exception e){
      return e.toString();
    }
    
    return "Request approved";
  }

  /**
  * Function to deny a registration request
  * @Returns feedback from the biometric function
  */
  @RequestMapping(method= RequestMethod.POST, value="/denyRequest")
  public String denyRequest(@RequestParam(value = "staffID", required = true) String staffID) {
    try{
      biometricSystem.deleteRegistrationRequest(staffID);
    }
    catch (Exception e){
      return e.toString();
    }
    
    return "Request denied";
  }

  /**
  * Function to check if logged in user is authorized to approve or deny registration requests
  * @Returns the string "authorized" or "not authorized"
  */
  @RequestMapping(method= RequestMethod.POST, value="/authorizedToAccessRequests")
  public String authorizedToAccessRequests(Principal principal,
                                           @RequestParam(value = "authorized", required = true) String authorized) {
    PermissionId permission = null;

    switch (authorized){
      case "REGISTRATION_APPROVE": {
        permission = PermissionId.REGISTRATION_APPROVE; 
        break;
      } 
      case "SUPER":{
        permission = PermissionId.SUPER; 
        break;
      } 
      case "REPORTS":{
        permission = PermissionId.REPORTS; 
        break;
      } 
      case "REGISTRATION":{
        permission = PermissionId.REGISTRATION; 
        break;
      } 
      case "USER_DELETE":
      {
        permission = PermissionId.USER_DELETE; 
        break;
      } 
      default:
      {
        permission = null;
      }
    }
   if(permissionManager.hasPermission(principal.getName(), permission))
   {
      return "authorized";
   } else {
      return "not authorized";
    }
  }

  /**
   * Function used to return all the users registered on the system
   * @return Returns string with the html code to display the users that are registered on the system
   */
  @RequestMapping(method= RequestMethod.POST, value="/getRegisteredUsers")
  public String getRegisteredUsers() {
    List<User> users = biometricSystem.getUsers();
    
    String returnPage = "";

    if(users != null){
      for(int i = 0; i < users.size(); i++)
      {
        String user = users.get(i).toString();

        returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\" id=\"userCol\"><p id=\"userID\">" + user + "</p></td>" +
                      "<td><button class='form-control deny denyBtn' type='submit' value='Deny'><i class = \"fa fa-times\"></i></button></td></tr>" +
                      "</tr>" +
                      "</table>";
      }
        
      if(users.size() == 0){
          returnPage += "<div id=\"permissionAuthorization\" class=\"wow fadeIn alert alert-warning\" data-wow-delay=\".2s\"><strong><i class=\"fa fa-exclamation-triangle\"></i>  INFORMATION: </strong><br/> No Users On The System</div>";


      }
    } else {
        returnPage += "<div id=\"permissionAuthorization\" class=\"wow fadeIn alert alert-warning\" data-wow-delay=\".2s\"><strong><i class=\"fa fa-exclamation-triangle\"></i>  INFORMATION: </strong><br/> No Users On The System</div>";
    }
    return returnPage;
  }

  /**
   * Function used to return the information of a staff member registered on the system to update his/her permissions
   * @return Returns string with the staff member's information to be read into the UI
   */
  @RequestMapping(method= RequestMethod.POST, value="/removeUser")
  public String removeUser(@RequestParam(value = "staffID", required = true) String staffID) {
    try{
      biometricSystem.removeUser(staffID);
    }
    catch(Exception e)
    {
      return e.toString();
    }

    return "User removed";
  }

  /**
  * Function to return all users on the system for permission altering
  */
  @RequestMapping(method= RequestMethod.POST, value="/getUsersForPermissionUpdate")
  public String getUsersForPermissionUpdate(){
    List<User> users = biometricSystem.getUsers();
    
    String returnPage = "";

    if(users != null){
      for(int i = 0; i < users.size(); i++)
      {
        String user = users.get(i).toString();

        returnPage += "<option>"+user+"</option>";
      }
        
      if(users.size() == 0){
        returnPage += "no users";      
      } 
    } else {
      returnPage += "no users";      
    }
    return returnPage;
  }

  /**
  * Function to return all possible permissions
  */
  @RequestMapping(method= RequestMethod.POST, value="/getAllPermissions")
  public String getAllPermissions(){
    PermissionId[] permissions = permissionManager.allPermissions();
    
    String returnPage = "";

    if(permissions != null){
      for(int i = 0; i < permissions.length; i++)
      {
        PermissionId permission = permissions[i];

        returnPage += "<option>"+permission+"</option>";
      }
        
      if(permissions.length == 0){
        returnPage += "no permissions";      
      } 
    } else {
      returnPage += "no permissions";      
    }
    return returnPage;
  }
  
  /**
  * Function to return all user's permissions
  */
  @RequestMapping(method= RequestMethod.POST, value="/getUserPermissions")
  public String getUserPermissions(@RequestParam(value = "staffID", required = true) String staffID) {
    List<Permission> permissions = permissionManager.permissionsForUser(staffID);
    
    String returnPage = "";

    if(permissions != null){
      for(int i = 0; i < permissions.size(); i++)
      {
        Permission permission = permissions.get(i);

        returnPage += "<table class=\"table table-striped table-bordered table-condensed form-group\">" +
                      "<tr>" +
                      "<td colspan=\"2\" id=\"userCol\"><p class=\"userPermission\">" + permission.toString() + "</p></td>" +
                      "<td><button class='form-control deny denyBtn' type='submit' value='Deny'><i class = \"fa fa-times\"></i></button></td></tr>" +
                      "</tr>" +
                      "</table>";
      }
        
      if(permissions.size() == 0){
        returnPage += "no permissions";      
      } 
    } else {
      returnPage += "no permissions";      
    }
    return returnPage;
  }

  /**
   * Function used to add a permission
   * @return the string "Permission added"
   */
  @RequestMapping(method= RequestMethod.POST, value="/addPermission")
  public String addPermission(@RequestParam(value = "permission", required = true) String stringPermission, 
                                @RequestParam(value = "staffID", required = true) String staffID) {

    PermissionId permission = null;

    switch (stringPermission){
      case "REGISTRATION_APPROVE": {
        permission = PermissionId.REGISTRATION_APPROVE; 
        break;
      } 
      case "SUPER":{
        permission = PermissionId.SUPER; 
        break;
      } 
      case "REPORTS":{
        permission = PermissionId.REPORTS; 
        break;
      } 
      case "REGISTRATION":{
        permission = PermissionId.REGISTRATION; 
        break;
      } 
      case "USER_DELETE":
      {
        permission = PermissionId.USER_DELETE; 
        break;
      } 
      default:
      {
        permission = null;
      }
    }

    permissionManager.addPermission(staffID, permission);
  
    return "Permission added";
  }

  /**
   * Function used to remove a permission
   * @return Returns string with the outcome of the function
   */
  @RequestMapping(method= RequestMethod.POST, value="/removePermision")
  public String removePermission(@RequestParam(value = "permission", required = true) String stringPermission, 
                                @RequestParam(value = "staffID", required = true) String staffID) {

    PermissionId permission = null;

    switch (stringPermission){
      case "REGISTRATION_APPROVE": {
        permission = PermissionId.REGISTRATION_APPROVE; 
        break;
      } 
      case "SUPER":{
        permission = PermissionId.SUPER; 
        break;
      } 
      case "REPORTS":{
        permission = PermissionId.REPORTS; 
        break;
      } 
      case "REGISTRATION":{
        permission = PermissionId.REGISTRATION; 
        break;
      } 
      case "USER_DELETE":
      {
        permission = PermissionId.USER_DELETE; 
        break;
      } 
      default:
      {
        permission = null;
      }
    }

    try{
      permissionManager.removePermission(staffID, permission);
    } 
    catch (Exception e){
      return e.toString();
    }

    return "Permission removed";
  }
}