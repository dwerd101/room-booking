package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.ProfileException;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.repository.ProfileRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements RoomService<Profile, Long> {
    private final ProfileRepository profileRepository;
    @Override
    public Profile save(Profile model) {
        return profileRepository.save(model);
    }

    @Override
    public Profile update(Profile model, Long id) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile deleteById(Long aLong) {
        return profileRepository.findById(aLong)
                .orElseThrow(() -> new ProfileException("Не найден ID"));
    }
}
