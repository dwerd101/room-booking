package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.VscRoomException;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.repository.VscRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VscRoomServiceImpl implements RoomService<VscRoom, Long> {
    private final VscRepository vscRepository;
    @Override
    public VscRoom save(VscRoom model) {
        return vscRepository.save(model);
    }

    @Override
    public VscRoom update(VscRoom model, Long id) {
        return null;
    }

    @Override
    public List<VscRoom> findAll() {
        return vscRepository.findAll();
    }

    @Override
    public VscRoom deleteById(Long aLong) {
        return vscRepository.findById(aLong)
                .orElseThrow(() -> new VscRoomException("Не найден ID"));
    }
}
