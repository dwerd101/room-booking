package ru.metrovagonmash.service;

import ru.metrovagonmash.model.dto.RegistrationDTO;

public interface RegistrationService extends RoomService<RegistrationDTO, Long>{
    void saveEmployeeAndProfile(RegistrationDTO model);
    boolean doesUserExist(RegistrationDTO model);
}
