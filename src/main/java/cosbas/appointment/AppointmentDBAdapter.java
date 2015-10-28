package cosbas.appointment;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Renette Ros}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */

public interface AppointmentDBAdapter  {

    @CacheEvict(value = "appointments",beforeInvocation = true, allEntries = true)
    <S extends Appointment> S save(S entity);
    @CacheEvict(value = "appointments",beforeInvocation = true, allEntries = true)
    <S extends Appointment> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "appointments",beforeInvocation = true, allEntries = true)
    void delete(Appointment entity);
    @CacheEvict(value = "appointments",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends Appointment> entities);
    @CacheEvict(value = "appointments",beforeInvocation = true, allEntries = true)
    void deleteAll();


    @Cacheable("appointments")
    List<Appointment> findByStaffID(String staffID);
    @Cacheable("appointments")
    Appointment findById(String id);
    @Cacheable("appointments")
    List<Appointment> findByStatusLike(String status);
    @Cacheable("appointments")
    List<Appointment> findByDateTimeBetween(LocalDateTime dateS, LocalDateTime dateE);
    @Cacheable("appointments")
    List<Appointment> findByStaffIDAndDateTimeBetween(String staffId, LocalDateTime dateS, LocalDateTime dateE);
    @Cacheable("appointments")
    Iterable<Appointment> findAll();
}
