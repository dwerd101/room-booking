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
import ru.metrovagonmash.model.VscRoom;
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

    // FIXME: 17.09.2021 Добавить отпарвку почту в другой сервис (создать сервис)
    @Override
    public RecordTableDTO save(RecordTableDTO recordTableDTO, User user) {
        Optional<RecordTable> overlappingRecordTable = recordTableRepository
                .findOverlappingRecordsByStartEventAndEndEvent(recordTableDTO.getStart(), recordTableDTO.getEnd(),
                        getRoomFromRecordTableDTO(recordTableDTO).getId());

        if (overlappingRecordTable.isPresent()) {
            throw new RecordTableBadRequestException("Данное время занято");
        }
        else {
            return mapper.toDTO(recordTableRepository.save(toRecordTable(recordTableDTO, user)));
        }
    }

    // FIXME: 01.10.2021 Проверить как работает если есть 2 одинаковые записи в разных комнатах
    @Override
    public RecordTableDTO delete(RecordTableDTO recordTableDTO, User user) {
        RecordTable recordTable= recordTableRepository.findByLoginAndId(user.getUsername(), recordTableDTO.getId())
                .orElseThrow(() -> new RecordTableBadRequestException("Не найдена запись"));
        recordTableRepository.delete(recordTable);
        return recordTableDTO;
    }


    @Override
    public boolean checkPermissionByLoginAndRecordId(String login, Long recordId) {
        return recordTableRepository.findById(recordId).get().getEmployeeId().getProfileId().getLogin().equals(login);
    }

    public List<RecordTableView> findAll() {
        return recordTableViewRepository.findAll();
    }

    private RecordTable toRecordTable (RecordTableDTO recordTableDTO, User user) {
        Employee employee = employeeRepository.findByLogin(user.getUsername()).orElseThrow(
                () -> new EmployeeBadRequestException("Не найден пользователь по логину"));
        RecordTable recordTable = mapper.toModel(recordTableDTO);
        recordTable.setEmail(employee.getEmail());
        recordTable.setIsActive(true);
        recordTable.setEmployeeId(employee);
        recordTable.setNumberRoomId(getRoomFromRecordTableDTO(recordTableDTO));
        return recordTable;
    }

    private VscRoom getRoomFromRecordTableDTO (RecordTableDTO recordTableDTO) {
        return vscRoomRepository.findByNumberRoom(Long.parseLong(recordTableDTO.getRoomId()))
                .orElseThrow(() -> new VscRoomBadRequestException("Не найден id комнаты"));
    }

}
