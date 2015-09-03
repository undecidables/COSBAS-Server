package cosbas.calendar_services;

/**
 * @author Jason Richard Evans
 */
public abstract class CalendarFactory {
    public CalendarService getService(CalendarType cal)
    {
        return getBean(cal.service);
    }

    protected abstract <T extends CalendarService> CalendarService getBean(Class<T> c);
}
