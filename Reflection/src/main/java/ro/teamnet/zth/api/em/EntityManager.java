package ro.teamnet.zth.api.em;

import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/29/2015
 */
public interface EntityManager {

    /**
     * Searches the database table corresponding to the given entity
     * for the record having the specified ID.
     * @param entityClass
     * @param id primary key value
     * @param <T>
     * @return instance of the entity corresponding to the found database record
     */
    <T> T findById(Class<T> entityClass, Long id);

    /**
     * Returns all records contained in the database table corresponding
     * to the given entity.
     * @param entityClass
     * @param <T>
     * @return list containing records
     */
    <T> List<T> findAll(Class<T> entityClass);

    <T> Object insert(T entity);

    <T> T update(T entity);

    void delete(Object entity);

}
