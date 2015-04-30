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

    /**
     * Inserts a new record into the database.
     * @param entity contains values to be inserted, except the ID as it
     *               will be automatically generated (AUTO_INCREMENT)
     * @param <T>
     * @return entity corresponding to the newly inserted record, including the ID
     */
    <T> Object insert(T entity);

    /**
     * Updates an existing database record corresponding to the given entity.
     * @param entity
     * @param <T>
     * @return <b>same entity</b> if the update operation has been successful;
     *         <br><b>null</b> otherwise
     */
    <T> T update(T entity);

    /**
     * Deletes an existing database record corresponding to the given entity.
     * @param entity
     */
    void delete(Object entity);

}
