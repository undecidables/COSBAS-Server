package cosbas.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * {@author Szymon}
 */
@Aspect
@Component
public class AspectLogger {

    static final Logger logger = LogManager.getRootLogger();
    //static final Logger infoLogger = LogManager.getLogger("infoLogLogger");

    @Pointcut("execution(* cosbas..*(..)) && !within(cosbas.calendar_services.authorization.*) && !within(cosbas.calendar_services.*) && !within(cosbas.calendar_services.services.*) ")
    public void selectAll() {

    }

    @Before(value = "selectAll()")
    public void beforeLogger(JoinPoint joinPoint) {
        logger.info("Accessing: " + joinPoint.toShortString());

        //infoLogger.trace("tracing");
    }

    @AfterThrowing(value = "selectAll()", throwing = "error")
    public void logAfterThrow(JoinPoint joinPoint, Throwable error) {
        System.out.println("here is an error");
        logger.error("Accessing: " + joinPoint.toShortString() + " got error: " + error.toString());
    }
    /**
     * TODO
     *
     * Log all errors to an error file
     * Log activity to activity file
     * Log log in and log out
     * Make it create above mentioned files and write to them.
     * Refine the interceptors, shouldnt/doesnt have to intercept everytihng.
     *
     * Pointcut on the following:
     * controllers
     *
     * talk to Renette
     */


}
