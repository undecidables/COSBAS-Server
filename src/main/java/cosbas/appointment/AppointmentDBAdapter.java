package cosbas.appointment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Renette
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface AppointmentDBAdapter extends CrudRepository<Appointment, String> {

    List<Appointment> findByStaffID(String staffID);
    Appointment findById(String id);
    List<Appointment> findByStatusLike(String status);
}
