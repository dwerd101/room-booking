package ru.metrovagonmash.service;

import org.springframework.security.core.userdetails.User;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.dto.RecordTableDTO;

import java.util.List;
import java.util.Optional;

public interface RecordTableService extends RoomService<RecordTableDTO, Long>{
  List<RecordTableDTO> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom();
  RecordTableDTO save(RecordTableDTO recordTableDTO, User user);
  RecordTableDTO delete(RecordTableDTO recordTableDTO);
  List<RecordTableDTO> findByNumberRoomId(Long id);
}
