package ru.metrovagonmash.service;

import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.dto.RecordTableDTO;

import java.util.List;

public interface RecordTableService extends RoomService<RecordTableDTO, Long>{
  List<RecordTableDTO> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom();
  void batchUpdateRecords(List<RecordTable> recordTableList);
}
