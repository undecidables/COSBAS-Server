package cosbas.calendar_services;

import cosbas.calendar_services.authorization.Authorizer;
import cosbas.calendar_services.authorization.GoogleAuthorization;
import cosbas.calendar_services.services.CalendarService;
import cosbas.calendar_services.services.GoogleCalendarService;

/**
 * @author Jason Richard Evans
 */
public enum CalendarType {
    GOOGLE (GoogleCalendarService.class, GoogleAuthorization.class);

    public final Class<? extends CalendarService> service;
    public final Class<? extends Authorizer> authorizer;

    CalendarType(Class <? extends CalendarService> service, Class<? extends Authorizer> auth){
       this.service = service;
       this.authorizer = auth;
    }
}
