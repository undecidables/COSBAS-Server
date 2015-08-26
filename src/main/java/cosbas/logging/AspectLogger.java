package cosbas.logging;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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

    static final Logger activityLogger = Logger.getLogger("activityLogger");
    static final Logger debugLogger = Logger.getLogger("debugLogger");


    @Pointcut("execution(* cosbas..*(..))")
    public void selectAll()
    {

    }

    @Before(value = "selectAll()")
    public void beforeLogger(JoinPoint joinPoint)
    {
        //System.out.println("This is the before of method: " + joinPoint.getSignature().toShortString());
        activityLogger.debug("This is the before of method: " + joinPoint.getSignature().toShortString());

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
     */
}
