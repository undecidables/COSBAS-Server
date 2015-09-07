package cosbas.calendar_services;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jason Richard Evans
 */
public interface CalendarDBAdapter extends CrudRepository<CalendarService, String> {
    List<CalendarService> findByStaffID(String staffID);
    CalendarService findById(String id);
}
