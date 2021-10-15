package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeAndRecordTableService;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeService;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;
import ru.metrovagonmash.service.RecordTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryRecordTableEmployeeAndRecordTableServiceImpl implements HistoryRecordTableEmployeeAndRecordTableService<RecordTableDTO,User,Long> {

    private final HistoryRecordTableEmployeeService historyRecordTableEmployeeService;
    private final RecordTableAndEmployeeService recordTableAndEmployeeService;
    private final RecordTableService recordTableService;

    @Transactional
    public RecordTableDTO save(RecordTableDTO model, User user) {
        RecordTableDTO recordTableDTO = recordTableAndEmployeeService.save(model, user);
        historyRecordTableEmployeeService.save(recordTableDTO);
        return recordTableDTO;
    }


    @Transactional
    public RecordTableDTO update(RecordTableDTO model, Long aLong) {
        model.setId(aLong);
        recordTableService.update(model, aLong);
        historyRecordTableEmployeeService.save(model);
        return model ;
    }

    //убрать

    public List<RecordTableDTO> findAll() {
        return null;
    }

    // убрать
    public RecordTableDTO deleteById(Long aLong) {
        return null;
    }
}
