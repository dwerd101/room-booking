package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.VscRoomException;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.repository.VscRoomRepository;
import ru.metrovagonmash.service.VscRoomService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VscRoomServiceImpl implements VscRoomService {
    private final VscRoomRepository vscRoomRepository;
    @Override
    public VscRoom save(VscRoom model) {
        return vscRoomRepository.save(model);
    }

    @Override
    public VscRoom update(VscRoom model, Long id) {
        model.setId(id);
        return vscRoomRepository.save(model);
    }

    @Override
    public List<VscRoom> findAll() {
        return vscRoomRepository.findAll();
    }

    @Override
    public VscRoom deleteById(Long aLong) {
        return vscRoomRepository.findById(aLong)
                .orElseThrow(() -> new VscRoomException("Не найден ID"));
    }

    @Override
    public void findByNumberRoomIfNotFoundByNumberRoomThrowException(Long number) {
        vscRoomRepository.findByNumberRoom(number).orElseThrow(
                () -> new VscRoomException("Не найдена комната"));
    }
}
