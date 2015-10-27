package cosbas.biometric.validators.fingerprint;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * {@author Vivian Venter}
 * The interface to get the data from the fingerprint repository
 * The two lists, bifurcations and endpoints, will be stored as Arraylists, hence We would have a list of Arraylists
 */

public interface FingerprintDAO extends CrudRepository<FingerprintTemplateData, String> {
    List<FingerprintTemplateData> findByStaffID(String staffID);
}
