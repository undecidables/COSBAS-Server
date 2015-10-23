package cosbas.biometric.request.registration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 *  {@author Renette Ros}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface RegisterRequestDAO extends CrudRepository<RegisterRequest, String> {

    @Cacheable("Register")
    RegisterRequest findByUserID(String userID);
    @Cacheable("Register")
    Iterable<RegisterRequest> findAll();
}
