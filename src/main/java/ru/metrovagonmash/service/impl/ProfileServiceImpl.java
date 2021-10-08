package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.ProfileException;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.repository.ProfileRepository;
import ru.metrovagonmash.service.ProfileService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
    //language=sql
    private final String SQL_CHANGE_ACCOUNT_NON_LOCKED = "update profile set account_non_locked = ? where id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final ProfileRepository profileRepository;

    @Override
    public Profile save(Profile model) {
        return profileRepository.save(model);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile deleteById(Long aLong) {
        Profile profile = profileRepository.findById(aLong)
                .orElseThrow(() -> new ProfileException("Не найден ID"));
        profileRepository.deleteById(aLong);
        return profile;
    }

    @Override
    public Profile findByLogin(String login) {
        return profileRepository.findByLogin(login)
                .orElseThrow(() -> new ProfileException("Профиль не найден"));
    }

    @Override
    public Profile findById(Long profId) {
        return profileRepository.findById(profId)
                .orElseThrow(() -> new ProfileException("Профиль не найден"));
    }

    @Override
    public boolean doesProfileExist(String login) {
        return profileRepository.findByLogin(login).isPresent();
    }

    @Override
    public Profile changeAccountNonLocked(Boolean account_non_locked, Long id ) {
        jdbcTemplate.update(SQL_CHANGE_ACCOUNT_NON_LOCKED, account_non_locked, id);
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileException("Не найден ID"));
    }

    // FIXME: 13.07.2021 Настроить корректную реализацию метода
    @Override
    public Profile update(Profile model, Long id) {

      /*  Profile profile =  profileRepository.findById(id)
                .orElseThrow(() -> new ProfileException("Не найден ID"));*/
        model.setId(id);
        return profileRepository.save(model);
    }
}
