package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.EmployeeException;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.exception.VscRoomException;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.EmployeeRepository;
import ru.metrovagonmash.repository.RecordTableRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;
import ru.metrovagonmash.repository.VscRepository;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;
import ru.metrovagonmash.service.RecordTableService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordTableAndEmployeeServiceImpl implements RecordTableAndEmployeeService {
    private final RecordTableRepository recordTableRepository;
    private final RecordTableViewRepository recordTableViewRepository;
    private final EmployeeRepository employeeRepository;
    private final VscRepository vscRepository;
    private final Mapper<RecordTable, RecordTableDTO> mapper;
    private final Mapper<RecordTableView, RecordTableDTO> mapperView;


    @Override
    public RecordTableDTO save(RecordTableDTO recordTableDTO, User user) {
        Optional<RecordTable> recordTable= recordTableRepository.findByLogin(user.getUsername());
        if(recordTable.isPresent()) {
            Optional<RecordTable> overlappingRecordTable = recordTableRepository
                    .findOverlappingRecordsByStartEventAndEndEvent(recordTableDTO.getStart(),recordTableDTO.getEnd());
            if (overlappingRecordTable.isPresent()) {
                throw new RecordTableException("Данное время занято");
            }
            else {
                recordTableDTO.setEmail(recordTable.get().getEmployeeId().getEmail());
                recordTableDTO.setIsActive(recordTable.get().getEmployeeId().getIsActive());
                RecordTable recordTable1 = mapper.toModel(recordTableDTO);
                recordTable1.setEmployeeId(recordTable.get().getEmployeeId());
                recordTable1.setNumberRoomId(vscRepository.findById(Long.parseLong(recordTableDTO.getRoomId()))
                        .orElseThrow(() -> new VscRoomException("Не найден id комнаты")));
                return mapper.toDTO(recordTableRepository.save(recordTable1));
            }
        }
        else {
           Employee employee =  employeeRepository.findByLogin(user.getUsername()).orElseThrow(
                   () ->new EmployeeException("Не найдена по логину запись"));
           recordTableDTO.setEmail(employee.getEmail());
           recordTableDTO.setIsActive(employee.getIsActive());
            RecordTable recordTable1 = mapper.toModel(recordTableDTO);
            recordTable1.setEmployeeId(employee);
            recordTable1.setNumberRoomId(vscRepository.findById(Long.parseLong(recordTableDTO.getRoomId()))
            .orElseThrow(()-> new VscRoomException("Не найден id комнаты")));
            return mapper.toDTO(recordTableRepository.save(recordTable1));
        }

    }

    @Override
    public RecordTableDTO delete(RecordTableDTO recordTableDTO, User user) {
        RecordTable recordTable= recordTableRepository.findByLoginAndStartEventAndEndEvent(
                user.getUsername(), recordTableDTO.getStart(), recordTableDTO.getEnd())
                .orElseThrow(() -> new RecordTableException("Не найдена запись"));
        recordTableRepository.delete(recordTable);
        return recordTableDTO;
    }

}
