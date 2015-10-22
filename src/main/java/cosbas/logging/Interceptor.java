package cosbas.logging;

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

    @Pointcut("execution(* cosbas.web.ReportsRestController.*(..)) && @annotation(cosbas.logging.AuthenticateReports))")
    public void toAuthenticateReporting()
    {

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
