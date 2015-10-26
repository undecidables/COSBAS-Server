package cosbas.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Renette
 */
public interface UserDAO {

    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    <S extends User> S save(S entity);
    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    <S extends User> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    void delete(User entity);
    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends User> entities);
    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    void deleteAll();
    @CacheEvict(value = "users",beforeInvocation = true, allEntries = true)
    void delete(String userID);

    long count();
    @Cacheable("users")
    Iterable<User> findAll();
    @Cacheable("users")
    User findByUserID(String userID);
    @Cacheable("users")
    User findOne(String userID);
}
