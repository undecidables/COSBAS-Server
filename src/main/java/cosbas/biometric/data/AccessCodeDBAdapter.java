package cosbas.biometric.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface AccessCodeDBAdapter extends CrudRepository<AccessCode,String>{
    AccessCode findByAppointmentID(String AppointmentID);
    AccessCode findById(String id);
}
