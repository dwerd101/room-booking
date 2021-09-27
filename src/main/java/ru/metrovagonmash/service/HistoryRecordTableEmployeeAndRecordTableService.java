package ru.metrovagonmash.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.dto.RecordTableDTO;

public interface HistoryRecordTableEmployeeAndRecordTableService<Model, User, Long>  {

    RecordTableDTO save(Model model, User user);



    RecordTableDTO update(Model model, Long aLong);
}
