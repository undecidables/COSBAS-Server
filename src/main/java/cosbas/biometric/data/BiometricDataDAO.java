package cosbas.biometric.data;

import cosbas.biometric.BiometricTypes;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of SpringData Repositories.
 */
public interface BiometricDataDAO {

    /**
     * Apply @CacheEvict annotation on functions which will alter the tables/collections in the database, apply @Cacheable annotations to functions that do not alter the database in any way.
     */

    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    <S extends BiometricData> S save(S entity);
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    <S extends BiometricData> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    void delete(BiometricData entity);
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends BiometricData> entities);
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    void deleteAll();
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    List<BiometricData> deleteByUserID(String userID);
    @CacheEvict(value = "Biometric-Data",beforeInvocation = true, allEntries = true)
    List<TemporaryAccessCode> deleteByValidToLessThan(LocalDateTime time);

    @Cacheable("Biometric-Data")
    Iterable<BiometricData> findAll();
    @Cacheable("Biometric-Data")
    List<BiometricData> findByUserID(String userID);
    @Cacheable("Biometric-Data")
    List<BiometricData> findByType (BiometricTypes type);
    @Cacheable("Biometric-Data")
    BiometricData findByData (byte[] data);
    

    @Cacheable("Biometric-Data")
    List<TemporaryAccessCode> findByAppointmentID(String appointmentID);

    @Cacheable("Biometric-Data")
    TemporaryAccessCode findById(String id);

}
