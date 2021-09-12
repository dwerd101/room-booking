package ru.metrovagonmash.mapper;

import org.springframework.stereotype.Component;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;

@Component
public class RecordTableViewMapper implements Mapper<RecordTableView, RecordTableDTO> {

    @Override
    public RecordTableDTO toDTO(RecordTableView recordTableView) {

        return RecordTableDTO.builder()
                .employeeName(recordTableView.getEmployeeName())
                .employeeMiddleName(recordTableView.getEmployeeMiddleName())
                .employeeSurname(recordTableView.getEmployeeSurname())
                .isActive(recordTableView.getIsActive())
                .vcsRoomNumberRoom(String.valueOf(recordTableView.getVcsRoomNumberRoom()))
                .title(recordTableView.getTitle())
                .start(recordTableView.getStartEvent())
                .end(recordTableView.getEndEvent())
                .build();
    }

    @Override
    public RecordTableView toModel(RecordTableDTO recordTableDTO) {
        return null;
    }
}
