package cosbas.calendar_services;

/**
 * @author Jason Richard Evans
 */
public enum CalendarType {
    GOOGLE (GoogleCalendarService.class);

    public final Class service;
    <T extends CalendarService> CalendarType(Class <T> theType){this.service = theType;}
}
