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
public interface BiometricDataDAO {


    <S extends BiometricData> S save(S entity);
    <S extends BiometricData> Iterable<S> save(Iterable<S> entities);


    Iterable<BiometricData> findAll();
    long count();
    void delete(BiometricData entity);
    void delete(Iterable<? extends BiometricData> entities);
    void deleteAll();

    @Cacheable("Biometric-User")
    List<BiometricData> findByUserID(String userID);
    @Cacheable("Biometric-Type")
    List<BiometricData> findByType (BiometricTypes type);
    @Cacheable("Biometric-Data")
    BiometricData findByData (byte[] data);

    @CacheEvict("Biometric-Data")

    List<BiometricData> deleteByUserID(String userID);



}
