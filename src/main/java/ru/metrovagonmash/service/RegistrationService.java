package ru.metrovagonmash.service;

import ru.metrovagonmash.model.dto.RegistrationDTO;

public interface RegistrationService {
    void saveEmployeeAndProfile(RegistrationDTO model);
    boolean doesUserExist(RegistrationDTO model);
}
