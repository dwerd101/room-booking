package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.PasswordConfirmationToken;
import ru.metrovagonmash.repository.PasswordConfirmationTokenRepository;
import ru.metrovagonmash.service.PasswordConfirmationTokenService;

import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Service
public class PasswordConfirmationTokenServiceImpl implements PasswordConfirmationTokenService {
    private final PasswordConfirmationTokenRepository passwordConfirmationTokenRepository;

    @Override
    public PasswordConfirmationToken findByToken(String token) {
        return passwordConfirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NoSuchElementException("Не найден токен"));
    }


    public PasswordConfirmationToken save(PasswordConfirmationToken passwordConfirmationToken) {

        return passwordConfirmationTokenRepository.save(passwordConfirmationToken);
    }

    @Override
    public PasswordConfirmationToken deleteById(Long aLong) {
        PasswordConfirmationToken passwordConfirmationToken = passwordConfirmationTokenRepository.findById(aLong)
                .orElseThrow(() -> new NoSuchElementException("Не найден токен"));
        passwordConfirmationTokenRepository.deleteById(aLong);
        return passwordConfirmationToken;
    }
}
