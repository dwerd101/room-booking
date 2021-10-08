package ru.metrovagonmash.service;

import org.springframework.security.core.userdetails.User;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;

import java.util.List;

public interface RecordTableAndEmployeeService  {
    RecordTableDTO save(RecordTableDTO recordTableDTO, User user);
    RecordTableDTO delete(RecordTableDTO recordTableDTO, User user);
    RecordTableDTO update(RecordTableDTO recordTableDTO, Long id);
    boolean checkPermissionByLoginAndRecordId(String login, Long recordId);
    List<RecordTableView> findAll();
}
