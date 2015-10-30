package cosbas.biometric.validators.facial;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * {@author Renette}
 */
public interface DataComponentDAO {
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    <S extends RecognizerDataComponent> S save(S entity);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    <S extends RecognizerDataComponent> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void delete(RecognizerDataComponent entity);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends RecognizerDataComponent> entities);
    @CacheEvict(value = "recognizer",beforeInvocation = true, allEntries = true)
    void deleteAll();

    @Cacheable("recognizer")
    RecognizerDataComponent findById(String id);
    @Cacheable("recognizer")
    List<CvMatStorage> findByFieldAndLinkedID(String field, String linkedID);
    @Cacheable("recognizer")
    CvMatStorage findFirstByFieldAndLinkedID(String field, String linkedID);
    @Cacheable("recognizer")
    NameStore findFirstByOrderBySavedDesc();
    @Cacheable("recognizer")
    Iterable<RecognizerDataComponent> findAll();
}
