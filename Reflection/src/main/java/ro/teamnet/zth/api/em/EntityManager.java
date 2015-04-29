package ro.teamnet.zth.api.em;

import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/29/2015
 */
public interface EntityManager {

    <T> T findById(Class<T> entityClass, Long id);

    <T> List<T> findAll(Class<T> entityClass);

    <T> Object insert(T entity);

    <T> T update(T entity);

    void delete(Object entity);

}
