package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.RecordRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordTableServiceImpl implements RecordTableService {
    private final RecordRepository recordRepository;
    private final RecordTableViewRepository recordTableViewRepository;
    private final Mapper<RecordTable, RecordTableDTO> mapper;
    private final Mapper<RecordTableView, RecordTableDTO> mapperView;

    @Override
    public RecordTableDTO save(RecordTableDTO model) {
        return mapper.toDTO(recordRepository.save(toRecordTable(model)));
    }



    @Override
    public RecordTableDTO update(RecordTableDTO model, Long aLong) {
        model.setId(aLong);
        return mapper.toDTO(recordRepository.save(mapper.toModel(model)));
    }

    @Override
    public List<RecordTableDTO> findAll() {
        return recordRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecordTableDTO deleteById(Long aLong) {
        return mapper.toDTO( recordRepository.findById(aLong)
                .orElseThrow(() -> new RecordTableException("Не найден ID")));
    }

    // изменить заглушку на будущее
    private RecordTable toRecordTable(RecordTableDTO model) {
        RecordTable recordTable = mapper.toModel(model);
        RecordTable temp = recordRepository.findByNumberRoomIdAndEmployeeId(recordTable.getNumberRoomId().getId(),
                recordTable.getEmployeeId().getId()).orElseThrow(() -> new RecordTableException("Не найден"));
        recordTable.setNumberRoomId(temp.getNumberRoomId());
        recordTable.setEmployeeId(temp.getEmployeeId());
        return recordTable;
    }

    @Override
    public List<RecordTableDTO> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom() {
        return recordTableViewRepository.findAll()
                .stream()
                .map(mapperView::toDTO)
                .collect(Collectors.toList());
    }




    /*
    @Override
    public RecordTable save(RecordTable model) {
        return recordRepository.save(model);
    }

    @Override
    public RecordTable update(RecordTable model, Long id) {
        return null;
    }

    @Override
    public List<RecordTable> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public RecordTable deleteById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RecordTableException("Не найден ID"));
    }

     */
}
