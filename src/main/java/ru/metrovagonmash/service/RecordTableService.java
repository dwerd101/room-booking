package ru.metrovagonmash.service;

import org.springframework.security.core.userdetails.User;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.dto.RecordTableDTO;

import java.util.List;
import java.util.Optional;

public interface RecordTableService extends RoomServiceCRUD<RecordTableDTO, Long>{
  List<RecordTableDTO> findAllByEmployeeFullNameAndRecordAndIsActiveAndNumberRoom();
  RecordTableDTO save(RecordTableDTO recordTableDTO, User user);
  RecordTableDTO delete(RecordTableDTO recordTableDTO);
  RecordTableDTO findById(Long id);
  List<RecordTableDTO> findByNumberRoom(Long id);
  void batchUpdateRecords(List<RecordTable> recordTableList);
}
