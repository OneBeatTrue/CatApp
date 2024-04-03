package DAO;

import org.hibernate.HibernateException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DAO<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    T save(T t);
    T update(T t);
    void delete(T t);
    public void openCurrentSessionWithTransaction();
    public void closeCurrentSessionWithTransaction();
}