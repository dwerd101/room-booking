package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.exception.VscRoomBadRequestException;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.repository.VscRoomRepository;
import ru.metrovagonmash.service.VscRoomService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VscRoomServiceImpl implements VscRoomService {
    private final VscRoomRepository vscRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public VscRoom save(VscRoom model) {
        return vscRepository.save(model);
    }

    @Override
    public VscRoom update(VscRoom model, Long id) {
        model.setId(id);
        return vscRepository.save(model);
    }

    @Override
    public List<VscRoom> findAll() {
        return vscRepository.findAll();
    }

    @Override
    public VscRoom deleteById(Long aLong) {
        VscRoom vscRoom = vscRepository.findById(aLong)
                .orElseThrow(() -> new VscRoomBadRequestException("Не найден ID"));
        vscRepository.deleteById(aLong);
        return vscRoom;
    }
    @Override
    public void findByNumberRoomIfNotFoundByNumberRoomThrowException(Long number) {
        vscRepository.findByNumberRoom(number).orElseThrow(
                () -> new VscRoomBadRequestException("Не найдена комната"));
    }

    @Override
    public VscRoom findById(Long aLong) {
        return vscRepository.findById(aLong)
                .orElseThrow(() -> new VscRoomBadRequestException("Не найден ID"));
    }

    @Transactional
    @Override
    public void batchUpdateVscRoom(List<VscRoom> vscRoomList) {
        jdbcTemplate.batchUpdate("" + "update vsc_room set is_active=?, number_room=? where id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setBoolean(1,vscRoomList.get(i).getIsActive());
                        ps.setLong(2,vscRoomList.get(i).getNumberRoom());
                        ps.setLong(3,vscRoomList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return vscRoomList.size();
                    }
                });
    }
}
