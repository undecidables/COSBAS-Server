package cosbas.logging;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import cosbas.notifications.Email;
import cosbas.notifications.Notifications;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
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
    public void toNotify()
    {

    }

    @Autowired
    Notifications notify;

    @Autowired
    private AppointmentDBAdapter appointmentRepository;

    ExecutorService exe = Executors.newFixedThreadPool(50);

    @AfterReturning(value = "toNotify()", returning = "result")
    public void afterNotify(JoinPoint joinPoint, Object result) {

        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();



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
                            ContactDetail contactDetailsStaff = null;

                            //create ContactDetail objects for visitors
                            for (String s : attendants) {
                                if (s.equals(attendants.get(attendants.size() - 1))) {
                                    contactDetailsStaff = new ContactDetail(ContactTypes.EMAIL, s);
                                } else {
                                    contactDetailsVisitor.add(new ContactDetail(ContactTypes.EMAIL, s));
                                }
                            }

                            List<String> visitorIDs = (List<String>) arguments[0];

                            notify.sendNotifications(contactDetailsVisitor, contactDetailsStaff, Notifications.NotificationType.REQUEST_APPOINTMENT, visitorIDs, tempAppointment, false);
                        }
                    }
                } else if (methodName.equals("approveAppointment")) {
                    if (result.toString().equals("Appointment approved")) {

                        Appointment tempAppointment = appointmentRepository.findById((String) arguments[0]);

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

                    }
                }

            }
        });

    }
}
