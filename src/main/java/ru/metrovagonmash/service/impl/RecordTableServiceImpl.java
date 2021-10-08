package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.EmployeeRepository;
import ru.metrovagonmash.repository.RecordTableRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;
import ru.metrovagonmash.service.RecordTableService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordTableServiceImpl implements RecordTableService {
    private final RecordTableRepository recordTableRepository;
    private final RecordTableViewRepository recordTableViewRepository;
    private final EmployeeRepository employeeRepository;
    private final Mapper<RecordTable, RecordTableDTO> mapper;
    private final Mapper<RecordTableView, RecordTableDTO> mapperView;

    @Override
    public RecordTableDTO save(RecordTableDTO model) {
        return mapper.toDTO(recordTableRepository.save(toRecordTable(model)));
    }



    @Override
    public RecordTableDTO update(RecordTableDTO model, Long aLong) {
        model.setId(aLong);

        RecordTable recordTable = recordTableRepository.findById(aLong)
                .orElseThrow(() -> new RecordTableException("Не найдена запись"));

        recordTable.setTitle(model.getTitle());
        recordTable.setEmail(model.getEmail());
        recordTable.setIsActive(model.getIsActive());
        recordTable.setStartEvent(model.getStart());
        recordTable.setEndEvent(model.getEnd());
        recordTableRepository.save(recordTable);

        return mapper.toDTO(recordTable);


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
                .orElseThrow(() -> new RecordTableException("Не найден ID")));
    }

    // изменить заглушку на будущее
    private RecordTable toRecordTable(RecordTableDTO model) {
        RecordTable recordTable = mapper.toModel(model);
        RecordTable temp = recordTableRepository.findByNumberRoomIdAndEmployeeId(recordTable.getNumberRoomId().getId(),
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

    @Override
    public RecordTableDTO save(RecordTableDTO recordTableDTO, User user) {
        Optional<RecordTable> recordTable= recordTableRepository.findByLogin(user.getUsername());
        if(recordTable.isPresent()) {
            recordTableDTO.setEmail(recordTable.get().getEmployeeId().getEmail());
            recordTableDTO.setIsActive(recordTable.get().getEmployeeId().getIsActive());
            RecordTable recordTable1 = mapper.toModel(recordTableDTO);
            recordTable1.setEmployeeId(recordTable.get().getEmployeeId());
            recordTable1.setNumberRoomId(recordTable.get().getNumberRoomId());
            return mapper.toDTO(recordTableRepository.save(recordTable1));
        }
        else {

        }

        throw new RecordTableException();
    }

    @Override
    public RecordTableDTO delete(RecordTableDTO recordTableDTO) {
       RecordTable recordTable = recordTableRepository.findByStartEventAndEndEvent(
               recordTableDTO.getStart(), recordTableDTO.getEnd()
       ).orElseThrow(() -> new RecordTableException("Не найдена запись"));
       recordTableRepository.delete(recordTable);
        return recordTableDTO;
    }

    @Override
    public List<RecordTableDTO> findByNumberRoomId(Long id) {
        return recordTableRepository.findAllByNumberRoomId(id)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public RecordTableDTO findById(Long id) {
        return mapper.toDTO(recordTableRepository.findById(id).orElseThrow(() -> new RecordTableException("Не найдена запись")));
    }

    @Override
    public List<RecordTableDTO> findByNumberRoom(Long id) {
        return recordTableRepository.findAllByNumberRoom(id)
                .stream()
                .map(mapper::toDTO)
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
