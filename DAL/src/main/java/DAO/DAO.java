package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DAO<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    Long save(T t);
    void update(T t);
    void delete(T t);
}
