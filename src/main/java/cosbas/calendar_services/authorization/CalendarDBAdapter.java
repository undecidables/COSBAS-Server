package cosbas.calendar_services.authorization;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * @author Jason Richard Evans
 */
public interface CalendarDBAdapter {

    @CacheEvict(value = "credentialWrapper",beforeInvocation = true, allEntries = true)
    <S extends CredentialWrapper> S save(S entity);
    @CacheEvict(value = "credentialWrapper",beforeInvocation = true, allEntries = true)
    <S extends CredentialWrapper> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "credentialWrapper",beforeInvocation = true, allEntries = true)
    void delete(CredentialWrapper entity);
    @CacheEvict(value = "credentialWrapper",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends CredentialWrapper> entities);
    @CacheEvict(value = "credentialWrapper",beforeInvocation = true, allEntries = true)
    void deleteAll();

    long count();

    @Cacheable("credentialWrapper")
    CredentialWrapper findByStaffID(String staffID);
    @Cacheable("credentialWrapper")
    List<CredentialWrapper> findAll();
}
