package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.EmployeeBadRequestException;
import ru.metrovagonmash.exception.RecordTableBadRequestException;
import ru.metrovagonmash.exception.VscRoomBadRequestException;
import ru.metrovagonmash.mapper.VCMapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.EmployeeRepository;
import ru.metrovagonmash.repository.RecordTableRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;
import ru.metrovagonmash.repository.VscRoomRepository;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordTableAndEmployeeServiceImpl implements RecordTableAndEmployeeService {
    private final RecordTableRepository recordTableRepository;
    private final RecordTableViewRepository recordTableViewRepository;
    private final EmployeeRepository employeeRepository;
    private final VscRoomRepository vscRoomRepository;
    private final VCMapper<RecordTable, RecordTableDTO> mapper;
    private final VCMapper<RecordTableView, RecordTableDTO> mapperView;


    @Override
    public RecordTableDTO save(RecordTableDTO recordTableDTO, User user) {
        Optional<RecordTable> recordTable= recordTableRepository.findByLogin(user.getUsername());
        Optional<RecordTable> overlappingRecordTable = recordTableRepository
                .findOverlappingRecordsByStartEventAndEndEvent(recordTableDTO.getStart(),recordTableDTO.getEnd(),
                        vscRoomRepository.findByNumberRoom(Long.parseLong(recordTableDTO.getRoomId()))
                                .orElseThrow(() -> new VscRoomBadRequestException("Не найден id комнаты")).getId());
        if(recordTable.isPresent()) {
            if (overlappingRecordTable.isPresent()) {
                throw new RecordTableBadRequestException("Данное время занято");
            }
            else {
                recordTableDTO.setEmail(recordTable.get().getEmployeeId().getEmail());
                recordTableDTO.setIsActive(recordTable.get().getEmployeeId().getIsActive());
                RecordTable recordTable1 = mapper.toModel(recordTableDTO);
                recordTable1.setEmployeeId(recordTable.get().getEmployeeId());
                recordTable1.setNumberRoomId(vscRoomRepository.findByNumberRoom(Long.parseLong(recordTableDTO.getRoomId()))
                        .orElseThrow(() -> new VscRoomBadRequestException("Не найден id комнаты")));
                return mapper.toDTO(recordTableRepository.save(recordTable1));
            }
        }
        else {
            if (overlappingRecordTable.isPresent()) {
                throw new RecordTableBadRequestException("Данное время занято");
            }
            else {
                Employee employee = employeeRepository.findByLogin(user.getUsername()).orElseThrow(
                        () -> new EmployeeBadRequestException("Не найдена по логину запись"));
                recordTableDTO.setEmail(employee.getEmail());
                recordTableDTO.setIsActive(employee.getIsActive());
                RecordTable recordTable1 = mapper.toModel(recordTableDTO);
                recordTable1.setEmployeeId(employee);
                recordTable1.setNumberRoomId(vscRoomRepository.findByNumberRoom(Long.parseLong(recordTableDTO.getRoomId()))
                        .orElseThrow(() -> new VscRoomBadRequestException("Не найден id комнаты")));
                return mapper.toDTO(recordTableRepository.save(recordTable1));
            }
        }

    }

    @Override
    public RecordTableDTO delete(RecordTableDTO recordTableDTO, User user) {
        RecordTable recordTable= recordTableRepository.findByLoginAndStartEventAndEndEvent(
                user.getUsername(), recordTableDTO.getStart(), recordTableDTO.getEnd())
                .orElseThrow(() -> new RecordTableBadRequestException("Не найдена запись"));
        recordTableRepository.delete(recordTable);
        return recordTableDTO;
    }

    @Override
    public RecordTableDTO update(RecordTableDTO recordTableDTO, Long id) {
        RecordTable recordTable = recordTableRepository.findById(id)
                .orElseThrow(() -> new RecordTableBadRequestException("Не найдена запись"));
        recordTableDTO.setId(id);


        return null;
    }

    @Override
    public boolean checkPermissionByLoginAndRecordId(String login, Long recordId) {
        return recordTableRepository.findById(recordId).get().getEmployeeId().getProfileId().getLogin().equals(login);
    }
    public List<RecordTableView> findAll() {
        return recordTableViewRepository.findAll();
    }



}
