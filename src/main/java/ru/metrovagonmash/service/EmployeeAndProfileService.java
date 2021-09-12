package ru.metrovagonmash.service;

import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;

public interface EmployeeAndProfileService {
    void update(EmployeeDTO employeeDTO, Profile profile);
    void deleteByProfileId(Long aLong);
    EmployeeDTO findEmployeeByProfileId(Long aLong);
    Profile findProfileById(Long aLong);
}
