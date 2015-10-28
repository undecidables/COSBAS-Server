package cosbas.db.repositories;


import cosbas.appointment.DeletedAppointment;
import cosbas.appointment.DeletedAppointmentDBAdapter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vivian Venter
 * Interface that handles fetching the deleted appointment objects from MongoDB and saving deleted appointment objects in the db.
 * Should not be implmented, Spring handles implementation.
 *
 * For more functions research the MongoRepository Interface.
 * Query Functions are defined in the DeletedAppointmentDBAdapter interface.
 */
public interface DeletedAppointmentRepository  extends MongoRepository<DeletedAppointment, String>, DeletedAppointmentDBAdapter {
}
