package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.VscRoom;

import java.util.Optional;

@Repository
public interface VscRoomRepository extends JpaRepository<VscRoom,Long> {
    Optional<VscRoom> findByNumberRoom(Long room);
}
