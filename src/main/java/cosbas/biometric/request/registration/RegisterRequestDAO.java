package cosbas.biometric.request.registration;

import cosbas.biometric.request.access.AccessRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface RegisterRequestDAO extends CrudRepository<RegisterRequest, String> {

    RegisterRequest findByPersonID(String PersonID);
}
