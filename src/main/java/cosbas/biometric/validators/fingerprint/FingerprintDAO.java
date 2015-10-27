package cosbas.biometric.validators.fingerprint;


import org.springframework.data.repository.CrudRepository;

/**
 * {@author Vivian Venter}
 * The interface to get the data from the fingerprint repository
 */

public interface FingerprintDAO extends CrudRepository<FingerprintTemplateData, String> {
    FingerprintTemplateData findByStaffID(String staffID);
}
