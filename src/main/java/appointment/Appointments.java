package appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Appointments class used to Request and cancel appointments. 
 * @author Elzahn
 */
@Service
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
    
    public String requestAppointment(List<String> visitorIDs, String staffID, LocalDateTime dateTime, String reason, int durationInMinutes)
    {
        Appointment a = new Appointment(staffID, visitorIDs,dateTime, durationInMinutes, reason);

        repository.save(a);
        
        return a.getId();
    }
    
    public void cancelAppointment(String cancelleeID, String appointmentID)
    {
        
    }
}