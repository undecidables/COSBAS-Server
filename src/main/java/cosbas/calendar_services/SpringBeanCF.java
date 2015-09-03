package cosbas.calendar_services;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jason Richard Evans
 */
public class SpringBeanCF extends CalendarFactory {
    @Autowired
    private BeanFactory beanFactory;

    protected <T extends CalendarService> CalendarService getBean(Class<T> type) {
        return beanFactory.getBean (type);
    }
}
