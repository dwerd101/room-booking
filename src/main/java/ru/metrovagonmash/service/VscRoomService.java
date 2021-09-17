package ru.metrovagonmash.service;

import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.VscRoom;

import java.util.Optional;

public interface VscRoomService extends RoomService<VscRoom, Long> {
   void findByNumberRoomIfNotFoundByNumberRoomThrowException(Long number);
}
