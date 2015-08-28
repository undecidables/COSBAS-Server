package cosbas.logging;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by simon on 2015-08-24.
 */
@Aspect
@Component
public class AspectLogger {

    static final Logger errorLogger = LogManager.getRootLogger();
    //static final Logger infoLogger = LogManager.getLogger("infoLogLogger");

    @Pointcut("execution(* cosbas..*(..))")
    public void selectAll()
    {

    }

    @Before(value = "selectAll()")
    public void beforeLogger(JoinPoint joinPoint)
    {
        errorLogger.error("uhm????");
        errorLogger.info("uhm info......");
        //infoLogger.trace("tracing");
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
