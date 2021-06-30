package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.repository.ProfileRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements RoomService<Profile, Long> {
    private final ProfileRepository profileRepository;
    @Override
    public Profile save(Profile model) {
        return null;
    }

    @Override
    public Profile update(Profile model) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile deleteById(Long aLong) {
        return null;
    }
}
