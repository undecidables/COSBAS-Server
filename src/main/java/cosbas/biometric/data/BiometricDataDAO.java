package cosbas.biometric.data;

import cosbas.biometric.BiometricTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of SpringData Repositories.
 */
public interface BiometricDataDAO extends CrudRepository<BiometricData, String> {

    List<BiometricData> findByUserID(String userID);
    List<BiometricData> findByType (BiometricTypes type);
    BiometricData findByData (byte[] data);

}
