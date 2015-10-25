package cosbas.appointment;

import org.springframework.data.repository.CrudRepository;


/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface DeletedAppointmentDBAdapter extends CrudRepository<DeletedAppointment, String> {

    DeletedAppointment findById(String id);
    DeletedAppointment findByAppointmentID(String id);
}
