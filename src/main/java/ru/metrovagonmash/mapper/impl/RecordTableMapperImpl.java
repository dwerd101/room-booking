package ru.metrovagonmash.mapper.impl;

import org.springframework.stereotype.Component;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.model.dto.RecordTableDTO;

@Component
public class RecordTableMapperImpl implements Mapper<RecordTable, RecordTableDTO> {
    @Override
    public RecordTableDTO toDTO(RecordTable recordTable) {
        return RecordTableDTO.builder()
                .id(recordTable.getId())
                .email(recordTable.getEmail())
                .title(recordTable.getTitle())
                .start(recordTable.getStartEvent())
                .end(recordTable.getEndEvent())
                .isActive(recordTable.getIsActive())
                .numberRoomId(recordTable.getNumberRoomId().getId())
                .employeeId(recordTable.getEmployeeId().getId())
                .build();
    }

    @Override
    public RecordTable toModel(RecordTableDTO recordTableDTO) {
        return RecordTable.builder()
                .id(recordTableDTO.getId())
                .email(recordTableDTO.getEmail())
                .title(recordTableDTO.getTitle())
                .startEvent(recordTableDTO.getStart())
                .endEvent(recordTableDTO.getEnd())
                .isActive(recordTableDTO.getIsActive())
                .numberRoomId(VscRoom.builder()
                        .id(recordTableDTO.getNumberRoomId())
                        .build())
                .employeeId(Employee.builder()
                        .id(recordTableDTO.getEmployeeId())
                        .build())
                .build();
    }
}
