package cosbas.calendar_services;

import cosbas.calendar_services.authorization.Authorizer;
import cosbas.calendar_services.services.CalendarService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@author Jason Richard Evans}
 * A Calendar factory implementation that wraps a Spring {@link BeanFactory} to create the Calendar Service and Authorization Beans
 */
@Service
public class SpringBeanCF extends CalendarFactory {
    @Autowired
    private BeanFactory beanFactory;

    protected <T extends CalendarService> T getServiceBean(Class<T> type) {
        return beanFactory.getBean (type);
    }

    protected <T extends Authorizer> T getAuthBean(Class<T> type) {
        return beanFactory.getBean (type);
    }
}
