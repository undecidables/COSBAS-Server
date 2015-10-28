package cosbas.calendar_services.authorization;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public interface CalendarDBAdapter {

    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    <S extends CredentialWrapper> S save(S entity);
    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    <S extends CredentialWrapper> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    void delete(CredentialWrapper entity);
    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    void delete(String id);
    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends CredentialWrapper> entities);
    @CacheEvict(value = "credential", beforeInvocation = true, allEntries = true)
    void deleteAll();

    @Cacheable("credential")
    long count();

    @Cacheable("credential")
    CredentialWrapper findByStaffID(String staffID);
    @Cacheable("credential")
    List<CredentialWrapper> findAll();
}
