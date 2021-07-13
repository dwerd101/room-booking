package ru.metrovagonmash.mapper;

import org.springframework.stereotype.Component;
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
                .record(recordTable.getRecord())
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
                .record(recordTableDTO.getRecord())
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
