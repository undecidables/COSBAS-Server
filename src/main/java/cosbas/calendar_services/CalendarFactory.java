package cosbas.calendar_services;

import cosbas.calendar_services.authorization.Authorizer;
import cosbas.calendar_services.services.CalendarService;

/**
 * {@author Jason Richard Evans}
 */
public abstract class CalendarFactory {
    public CalendarService getService(CalendarType cal)
    {
        return getServiceBean(cal.service);
    }
    public Authorizer getAuthorizer(CalendarType calendarType) {
        return  getAuthBean(calendarType.authorizer);
    }

    protected abstract <T extends CalendarService> T getServiceBean(Class<T> c);
    protected abstract <T extends Authorizer> T getAuthBean(Class<T> c);

}
