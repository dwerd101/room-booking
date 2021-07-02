package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.repository.RecordRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RoomService<RecordTable, Long> {
    private final RecordRepository recordRepository;
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
}
