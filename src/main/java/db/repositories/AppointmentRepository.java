package db.repositories;

import appointment.Appointment;
import appointment.AppointmentDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Renette
 * Interface that handles fetching Appointment Objects from the mongo DB and saving appointment objects in the db.
 * Should not be implmented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AppointmentDBAdapter interface.
 */
public interface AppointmentRepository  extends MongoRepository<Appointment, String>, AppointmentDBAdapter {
}
