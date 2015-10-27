package cosbas.logging;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import cosbas.appointment.DeletedAppointment;
import cosbas.appointment.DeletedAppointmentDBAdapter;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.data.TemporaryAccessCode;
import cosbas.notifications.Email;
import cosbas.notifications.Notifications;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import cosbas.user.User;
import cosbas.user.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cosbas.permissions.PermissionId;
import cosbas.permissions.PermissionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;


/**
 * {@author Szymon}
 */
@Aspect
@Component
public class Interceptor {

    static final Logger logger = LogManager.getRootLogger();

    /**
     * Pointcut and methods defined below are for logging purposes
     */
    @Pointcut("execution(* cosbas..*(..)) && !within(cosbas.calendar_services..*) && !within(cosbas.biometric..*)")
    public void toLog() {

    }

    @Before(value = "toLog()")
    public void beforeLogger(JoinPoint joinPoint) {
        logger.info(joinPoint.toShortString());
    }

    @AfterThrowing(value = "toLog()", throwing = "error")
    public void afterThrowLogger(JoinPoint joinPoint, Throwable error) {
        logger.error(joinPoint.toShortString() + " With Exception: " + error.toString());
    }


    @Pointcut("execution(* cosbas..*(..)) && @annotation(cosbas.notifications.Notify))")
    public void toNotify() {
		
	}

    @Autowired
    Notifications notify;

    @Autowired
    private AppointmentDBAdapter appointmentRepository;

    @Autowired
    private DeletedAppointmentDBAdapter deletedAppointmentRepository;

    @Autowired
    private UserDAO userRepository;
    

    ExecutorService exe = Executors.newFixedThreadPool(50);

    @AfterReturning(value = "toNotify()", returning = "result")
    public void afterNotify(JoinPoint joinPoint, Object result) {

        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        System.out.println(result.toString());

        exe.execute(new Runnable() {
            @Override
            public void run() {

                notify.setEmail(new Email());

                if (methodName.equals("requestAppointment")) {
                    if (!result.toString().equals("Time not available")) {

                        String[] results = result.toString().split(" ");
                        String id = results[1];

                        Appointment tempAppointment = appointmentRepository.findById(id);

                        if (tempAppointment != null) {
                            List<String> attendants = tempAppointment.getVisitorIDs();

                            ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
                            ArrayList<ContactDetail> contactDetailsStaff = null;

                            int loopCond = attendants.size();
                            if (contactDetailsStaff != null) {
                                loopCond -= contactDetailsStaff.size();
                            }

                            //create ContactDetail objects for visitors
                            for (int i = 0; i < loopCond; i++) {
                                contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, attendants.get(i)));
                            }

                            List<String> visitorIDs = (List<String>) arguments[0];

                            User user = userRepository.findByUserID(tempAppointment.getStaffID());
                            if (user != null) {
                                contactDetailsStaff = (ArrayList<ContactDetail>) user.getContact();
                                notify.sendStaffNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.REQUEST_APPOINTMENT,visitorIDs,tempAppointment,false);
                            }

                            notify.sendVisitorNotifications(contactDetailsVisitor, Notifications.NotificationType.REQUEST_APPOINTMENT, visitorIDs, tempAppointment, false);
                        }
                    }
                } else if (methodName.equals("approveAppointment")) {
                    if (result.toString().equals("Appointment approved")) {

                        System.out.println("Notification");

                        /*
                        Appointment tempAppointment = appointmentRepository.findById((String) arguments[0]);
                        List<TemporaryAccessCode> codes = codeRepository.findByAppointmentID(tempAppointment.getId());

                        System.out.println();
                        for (TemporaryAccessCode c: codes) {
                            System.out.println(c.getData().toString());
                        }

                        System.out.println();
                        for (TemporaryAccessCode c: codes) {
                            System.out.println(c.toString());
                        }

                        List<String> attendants = tempAppointment.getVisitorIDs();

                        ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
                        ContactDetail contactDetailsStaff = null;

                        //create ContactDetail objects for visitors
                        for(String s: attendants) {
                            if (s.equals(attendants.get(attendants.size()-1))) {
                                contactDetailsStaff = new ContactDetail(ContactTypes.EMAIL,s);
                            }
                            else {
                                contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                            }
                        }

                        notify.sendNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.APPROVE_APPOINTMENT, null ,tempAppointment, false);
                        */
                    }
                } else if (methodName.equals("denyAppointment")) {
                    if (result.toString().equals("Appointment denied")) {

                        DeletedAppointment tempAppointment = deletedAppointmentRepository.findByAppointmentID((String) arguments[0]);
                        List<String> attendants = tempAppointment.getVisitorIDs();

                        ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
                        ArrayList<ContactDetail> contactDetailsStaff = null;

                        //create ContactDetail objects for visitors
                        for (String s : attendants) {
                            contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                        }

                        User user = userRepository.findByUserID(tempAppointment.getStaffID());
                        if (user != null) {
                            contactDetailsStaff = (ArrayList<ContactDetail>) user.getContact();
                            notify.sendStaffNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.DENY_APPOINTMENT,null,tempAppointment,false);
                        }

                        notify.sendVisitorNotifications(contactDetailsVisitor, Notifications.NotificationType.DENY_APPOINTMENT, null, tempAppointment, false);

                    }
                } else if (methodName.equals("cancelAppointment")) {
                    if (result.toString().contains("Appointment has been cancelled. {")) {

                        DeletedAppointment tempAppointment = deletedAppointmentRepository.findByAppointmentID((String) arguments[1]);

                        List<String> attendants = tempAppointment.getVisitorIDs();

                        ArrayList<ContactDetail> contactDetailsVisitor = new ArrayList<>();
                        ArrayList<ContactDetail> contactDetailsStaff = null;

                        //create ContactDetail objects for visitors
                        for (String s : attendants) {
                            contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                        }

                        String[] results = result.toString().split(" ");
                        String byWhom = results[4];

                        if (byWhom.equals("{Staff}")) {

                            User user = userRepository.findByUserID(tempAppointment.getStaffID());
                            if (user != null) {
                                contactDetailsStaff = (ArrayList<ContactDetail>) user.getContact();
                                notify.sendStaffNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.CANCEL_APPOINTMENT,null,tempAppointment,true);
                            }
                            notify.sendVisitorNotifications(contactDetailsVisitor, Notifications.NotificationType.CANCEL_APPOINTMENT, null, tempAppointment, true);

                        } else if (byWhom.equals("{Visitor}")) {

                            User user = userRepository.findByUserID(tempAppointment.getStaffID());
                            if (user != null) {
                                contactDetailsStaff = (ArrayList<ContactDetail>) user.getContact();
                                notify.sendStaffNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.CANCEL_APPOINTMENT,null,tempAppointment,false);
                            }
                            notify.sendVisitorNotifications(contactDetailsVisitor, Notifications.NotificationType.CANCEL_APPOINTMENT, null, tempAppointment, false);
                        }
                    }
                }

            }
        });

    }

    @Pointcut("execution(* cosbas.web.ReportsRestController.*(..)) && @annotation(cosbas.logging.AuthenticateReports))")
    public void toAuthenticateReporting(){

    }

	@Autowired
    PermissionManager permissionManager;

    @Before(value = "toAuthenticateReporting() && args(principal,..)")
    public void authenticateReporting(JoinPoint joinPoint, Principal principal)
    {
        String userId = principal.getName();
        if(permissionManager.hasPermission(userId, PermissionId.REPORTS))
        {

        }
        else
        {
            throw new RuntimeException("Access Denied");
        }
    }
}
