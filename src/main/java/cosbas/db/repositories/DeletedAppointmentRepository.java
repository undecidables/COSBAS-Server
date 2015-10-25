package cosbas.db.repositories;


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
