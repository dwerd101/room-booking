package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.RecordTableRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;
import ru.metrovagonmash.service.RecordTableService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordTableServiceImpl implements RecordTableService {
    private final RecordTableRepository recordTableRepository;
    private final RecordTableViewRepository recordTableViewRepository;
    private final Mapper<RecordTable, RecordTableDTO> mapper;
    private final Mapper<RecordTableView, RecordTableDTO> mapperView;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public RecordTableDTO save(RecordTableDTO model) {
        return mapper.toDTO(recordTableRepository.save(toRecordTable(model)));
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
        RecordTableDTO recordTableDTO = mapper.toDTO( recordTableRepository.findById(aLong)
                .orElseThrow(() -> new RecordTableException("Не найден ID")));
        recordTableRepository.deleteById(aLong);
        return recordTableDTO;
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
    public List<RecordTableDTO> findByNumberRoom(Long id) {
        return recordTableRepository.findAllByNumberRoom(id)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecordTableDTO findById(Long id) {
        return mapper.toDTO(recordTableRepository.findById(id).orElseThrow(() -> new RecordTableException("Не найдена запись")));
    }




    @Transactional
    @Override
    public void batchUpdateRecords(List<RecordTable> recordTableList) {
        jdbcTemplate.batchUpdate("" + "update record_table set employee_id=?, number_room_id=?, " +
                        "is_active=?, email=?, title=?, start_event=?, end_event=? where id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1,recordTableList.get(i).getEmployeeId().getId());
                        ps.setLong(2,recordTableList.get(i).getNumberRoomId().getId());
                        ps.setBoolean(3,recordTableList.get(i).getIsActive());
                        ps.setString(4,recordTableList.get(i).getEmail());
                        ps.setString(5,recordTableList.get(i).getTitle());
                        ps.setTimestamp(6,Timestamp.from(recordTableList.get(i).getStartEvent().toInstant()));
                        ps.setTimestamp(7,Timestamp.from(recordTableList.get(i).getEndEvent().toInstant()));
                        ps.setLong(8,recordTableList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return recordTableList.size();
                    }
                });
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
