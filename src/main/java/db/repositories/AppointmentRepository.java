package db.repositories;

import appointment.Appointment;
import appointment.AppointmentDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Renette
 * Interface that handles fetching appointment Objects from the mongo DB and saving appointment objects in the db.
 * Should not be implmented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AppointmentDBAdapter interface.
 */
@Repository
public interface AppointmentRepository  extends MongoRepository<Appointment, String>, AppointmentDBAdapter {
}
