package ru.metrovagonmash.service;

import ru.metrovagonmash.model.dto.EmployeeDTO;

public interface EmployeeService extends RoomService<EmployeeDTO, Long> {
    boolean isPresentByDepartmentId(Long aLong);
    EmployeeDTO findEmployeeByProfileId(Long aLong);
    EmployeeDTO findById(Long aLong);
}
