package cosbas.biometric.validators.facial;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * {@author Renette}
 */
public interface RecognizerDAO extends CrudRepository<RecognizerData, String> {

    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    <S extends RecognizerData> S save(S entity);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    <S extends RecognizerData> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void delete(RecognizerData entity);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends RecognizerData> entities);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void deleteAll();

    @Cacheable("recognizer")
    RecognizerData findFirstByOrderByUpdatedDesc();
    @Cacheable("recognizer")
    Iterable<RecognizerData> findAll();
}
