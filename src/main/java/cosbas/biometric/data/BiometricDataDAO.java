package cosbas.biometric.data;

import cosbas.biometric.BiometricTypes;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of SpringData Repositories.
 */
public interface BiometricDataDAO extends CrudRepository<BiometricData, String> {
    @Cacheable("Biometric-User")
    List<BiometricData> findByUserID(String userID);
    @Cacheable("Biometric-Type")
    List<BiometricData> findByType (BiometricTypes type);
    @Cacheable("Biometric-Data")
    BiometricData findByData (byte[] data);

    @CacheEvict("Biometric-Data")
    List<BiometricData> deleteByUserID(String userID);

    @Override
    @CacheEvict("Biometric-Data")
    <T extends BiometricData> T save(T d);

    @Cacheable("Biometric-Data")
    List<TemporaryAccessCode> findByAppointmentID(String appointmentID);

    @Cacheable("Biometric-Data")
    TemporaryAccessCode findById(String id);
}
