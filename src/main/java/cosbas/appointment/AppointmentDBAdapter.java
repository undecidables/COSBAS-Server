package cosbas.appointment;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Renette Ros}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface AppointmentDBAdapter extends CrudRepository<Appointment, String> {

    List<Appointment> findByStaffID(String staffId);
    Appointment findById(String id);
    List<Appointment> findByStatusLike(String status);
    List<Appointment> findByDateTimeBetween(LocalDateTime dateS, LocalDateTime dateE);
    List<Appointment> findByStaffIDAndDateTimeBetween(String staffId, LocalDateTime dateS, LocalDateTime dateE);
}
