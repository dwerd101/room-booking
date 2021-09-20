package ru.metrovagonmash.service;

import ru.metrovagonmash.model.PasswordConfirmationToken;

public interface PasswordConfirmationTokenService {
    PasswordConfirmationToken findByToken(String token);
    PasswordConfirmationToken save(PasswordConfirmationToken passwordConfirmationToken);
    PasswordConfirmationToken deleteById(Long aLong);
}
