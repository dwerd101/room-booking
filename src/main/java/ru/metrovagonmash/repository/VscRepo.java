package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.VscRoom;

@Repository
public interface VscRepo extends JpaRepository<VscRoom,Long> {
}
