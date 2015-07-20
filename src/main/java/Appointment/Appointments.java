package appointment;

import org.springframework.beans.factory.annotation.Autowired;
import web.mongo_repositories.AppointmentRepository;

import java.time.LocalDateTime;

/**
 * Appointments class used to Request and cancel appointments. 
 * @author Elzahn
 */

public class Appointments 
{

    /**
     * The database adapter/repository to use.
     */
    @Autowired
    private AppointmentDBAdapter repository;

    /**
     * Setter based dependency injection since mongo automatically creates the bean.
     * @param repository The repository to be injected.
     */
    public void setRepository(AppointmentDBAdapter repository) {
        this.repository = repository;
    }
    
    public String requestAppointment(String appointerID, String staffID, LocalDateTime dateTime, String reason, int durationInMinutes)
    {
        String appointmentID = "";
        
        return appointmentID;
    }
    
    public void cancelAppointment(String cancelleeID, String appointmentID)
    {
        
    }
}