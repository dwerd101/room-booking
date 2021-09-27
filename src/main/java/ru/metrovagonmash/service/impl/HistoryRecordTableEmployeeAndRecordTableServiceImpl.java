package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeAndRecordTableService;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeService;
import ru.metrovagonmash.service.RecordTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryRecordTableEmployeeAndRecordTableServiceImpl implements HistoryRecordTableEmployeeAndRecordTableService {

    private final HistoryRecordTableEmployeeService historyRecordTableEmployeeService;
    private final RecordTableService recordTableService;

    @Transactional
    public RecordTableDTO save(RecordTableDTO model, User user) {
        recordTableService.save(model, user);
        historyRecordTableEmployeeService.save(model);
        return model ;
    }


    @Transactional
    public RecordTableDTO update(RecordTableDTO model, Long aLong) {
        model.setId(aLong);
        recordTableService.save(model);
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
