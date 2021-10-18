package ru.metrovagonmash.service;

import ru.metrovagonmash.model.VscRoom;



import java.util.List;

public interface VscRoomService extends RoomService<VscRoom, Long> {
   void findByNumberRoomIfNotFoundByNumberRoomThrowException(Long number);
   VscRoom findById(Long aLong);
    void batchUpdateVscRoom(List<VscRoom> vscRoomList);
}
