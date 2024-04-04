package DAO;

import org.hibernate.HibernateException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a generic DAO (Data Access Object) interface for managing entities of type T.
 *
 * @param <T> The type of entity managed by this DAO.
 */
public interface DAO<T> {

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @return An Optional containing the entity if found, otherwise empty.
     */
    Optional<T> get(Long id);

    /**
     * Retrieves all entities of type T.
     *
     * @return A list containing all entities of type T.
     */
    List<T> getAll();

    /**
     * Saves or updates an entity.
     *
     * @param t The entity to be saved or updated.
     * @return The saved or updated entity.
     */
    T save(T t);

    /**
     * Updates an existing entity.
     *
     * @param t The entity to be updated.
     * @return The updated entity.
     */
    T update(T t);

    /**
     * Deletes an entity.
     *
     * @param t The entity to be deleted.
     */
    void delete(T t);

    /**
     * Opens a new session with an active transaction.
     */
    public void openCurrentSessionWithTransaction();

    /**
     * Closes the current session and commits the transaction.
     */
    public void closeCurrentSessionWithTransaction();
}