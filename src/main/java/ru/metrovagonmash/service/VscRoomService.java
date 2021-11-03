package ru.metrovagonmash.service;

import ru.metrovagonmash.model.VscRoom;



import java.util.List;

public interface VscRoomService extends RoomServiceCRUD<VscRoom, Long> {
   void findByNumberRoomIfNotFoundByNumberRoomThrowException(Long number);
   VscRoom findById(Long aLong);
   VscRoom findByNumberRoomId(Long aLong);
    void batchUpdateVscRoom(List<VscRoom> vscRoomList);
}
