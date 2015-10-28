package cosbas.permissions;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  {@author Renette}
 */

public interface PermissionsDAO  {


    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    <S extends Permission> S save(S entity);
    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    <S extends Permission> Iterable<S> save(Iterable<S> entities);
    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    void delete(Permission entity);
    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    void delete(Iterable<? extends Permission> entities);
    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    void deleteAll();
    @CacheEvict(value = "permissions",beforeInvocation = true, allEntries = true)
    List<Permission> deleteByUserIDAndPermission(String userID, PermissionId permission);

    @Cacheable("permissions")
    Iterable<Permission> findAll();
    @Cacheable("permissions")
    List<Permission> findByUserID(String userID);
    @Cacheable("permissions")
    List<Permission> findByPermission(PermissionId permission);
    @Cacheable("permissions")
    Permission findByUserIDAndPermission(String userID, PermissionId permission);

}
