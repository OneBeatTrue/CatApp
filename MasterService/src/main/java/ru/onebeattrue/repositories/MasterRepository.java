package ru.onebeattrue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onebeattrue.entities.master.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
}
