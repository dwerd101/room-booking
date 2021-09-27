package ru.metrovagonmash.mapper.impl;

import org.springframework.stereotype.Component;
import ru.metrovagonmash.mapper.HistoryRecordTableEmpoyeeMapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.HistoryRecordTableEmployee;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.model.dto.HistoryRecordTableEmployeeDTO;
import ru.metrovagonmash.model.dto.RecordTableDTO;

@Component
public class HistoryRecordTableEmployeeImpl implements HistoryRecordTableEmpoyeeMapper {
    @Override
    public RecordTableDTO toDTO(HistoryRecordTableEmployee recordTable) {
        return RecordTableDTO.builder()
                .id(recordTable.getId())
                .email(recordTable.getEmail())
                .title(recordTable.getTitle())
                .start(recordTable.getStartEvent())
                .end(recordTable.getEndEvent())
                .isActive(recordTable.getIsActive())
                .numberRoomId(recordTable.getNumberRoomId())
                .employeeId(recordTable.getEmployeeId())
                .build();
    }

    @Override
    public HistoryRecordTableEmployee toModel(RecordTableDTO recordTableDTO) {
        return HistoryRecordTableEmployee.builder()
                .id(recordTableDTO.getId())
                .email(recordTableDTO.getEmail())
                .title(recordTableDTO.getTitle())
                .startEvent(recordTableDTO.getStart())
                .endEvent(recordTableDTO.getEnd())
                .isActive(recordTableDTO.getIsActive())
                .numberRoomId(recordTableDTO.getNumberRoomId())
                .employeeId(recordTableDTO.getEmployeeId())
                .build();
    }
}
