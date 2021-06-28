package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.repository.RecordRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RoomService<RecordTable, Long> {
    private final RecordRepository recordRepository;
    @Override
    public RecordTable save(RecordTable model) {
        return null;
    }

    @Override
    public RecordTable update(RecordTable model) {
        return null;
    }

    @Override
    public List<RecordTable> findAll() {
        return null;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return null;
    }
}
