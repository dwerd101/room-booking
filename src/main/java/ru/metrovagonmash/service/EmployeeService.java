package ru.metrovagonmash.service;

import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.dto.EmployeeDTO;

import java.util.Optional;

public interface EmployeeService extends RoomService<EmployeeDTO, Long> {
    EmployeeDTO findByProfileID(Long profileID);
    EmployeeDTO findByLogin(String login);
    Boolean isPresentByDepartmentId(Long id);
    EmployeeDTO findById(Long aLong);
   EmployeeDTO findEmployeeByProfileId(Long id);
}
