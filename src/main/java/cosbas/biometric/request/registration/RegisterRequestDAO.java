package cosbas.biometric.request.registration;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 *  {@author Renette Ros}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface RegisterRequestDAO{

    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    <S extends RegisterRequest> S save(S entity);
    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    <S extends RegisterRequest> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    void delete(RegisterRequest entity);
    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends RegisterRequest> entities);
    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    void deleteAll();
    @CacheEvict(value = "registerRequest",beforeInvocation = true, allEntries = true)
    void delete(String userID);


    @Cacheable("registerRequest")
    long count();

    @Cacheable("registerRequest")
    RegisterRequest findByUserID(String userID);
    @Cacheable("registerRequest")
    Iterable<RegisterRequest> findAll();
}
