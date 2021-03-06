package cosbas.db.repositories;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Renette
 * Interface that handles fetching appointment Objects from the mongo DB and saving appointment objects in the db.
 * Should not be implmented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the AppointmentDBAdapter interface.
 */
interface AppointmentRepository  extends MongoRepository<Appointment, String>, AppointmentDBAdapter {
}
