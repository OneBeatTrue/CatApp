package ru.onebeattrue.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.onebeattrue.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onebeattrue.models.Color;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    @Query("SELECT c FROM Cat c WHERE c.color = ?1")
    List<Cat> findAllByColor(Color color);
}
