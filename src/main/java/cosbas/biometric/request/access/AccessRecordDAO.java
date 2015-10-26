package cosbas.biometric.request.access;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  {@author Vivian Venter}
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Query functions are declared for SpringData repositories.
 */
public interface AccessRecordDAO{


    @CacheEvict(value = "access",beforeInvocation = true, allEntries = true)
    <S extends AccessRecord> S save(S entity);
    @CacheEvict(value = "access",beforeInvocation = true, allEntries = true)
    <S extends AccessRecord> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "access",beforeInvocation = true, allEntries = true)
    void delete(AccessRecord entity);
    @CacheEvict(value = "access",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends AccessRecord> entities);
    @CacheEvict(value = "access",beforeInvocation = true, allEntries = true)
    void deleteAll();


    @Cacheable("access")
    Iterable<AccessRecord> findAll();
    @Cacheable("access")
    List<AccessRecord> findByDoorID(String DoorID);
    @Cacheable("access")
    List<AccessRecord> findByAction(String Action);
    @Cacheable("access")
    List<AccessRecord> findByUserID(String userID);
    @Cacheable("access")
    AccessRecord findById(String id);
    @Cacheable("access")
    List<AccessRecord> findByDateTimeBetween(LocalDateTime dateS, LocalDateTime dateE);
    @Cacheable("access")
    List<AccessRecord> findByUserIDAndDateTimeBetween(String userID, LocalDateTime dateS, LocalDateTime dateE);
	
}
