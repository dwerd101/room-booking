package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.RecordTableBadRequestException;
import ru.metrovagonmash.mapper.VCMapper;
import ru.metrovagonmash.model.HistoryRecordTableEmployee;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.HistoryRecordTableEmployeeRepository;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryRecordTableEmployeeServiceImpl implements HistoryRecordTableEmployeeService {

    private final HistoryRecordTableEmployeeRepository recordTableRepository;
    private final VCMapper<HistoryRecordTableEmployee, RecordTableDTO> mapper;


    @Override
    public RecordTableDTO save(RecordTableDTO model) {
        return mapper.toDTO(recordTableRepository.save(mapper.toModel(model)));
    }



    @Override
    public RecordTableDTO update(RecordTableDTO model, Long aLong) {
        model.setId(aLong);
        return mapper.toDTO(recordTableRepository.save(mapper.toModel(model)));
    }

    @Override
    public List<RecordTableDTO> findAll() {
        return recordTableRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecordTableDTO deleteById(Long aLong) {
        return mapper.toDTO( recordTableRepository.findById(aLong)
                .orElseThrow(() -> new RecordTableBadRequestException("Не найден ID")));
    }



}
