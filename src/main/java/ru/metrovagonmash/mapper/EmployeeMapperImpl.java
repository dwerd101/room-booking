package ru.metrovagonmash.mapper;

import org.springframework.stereotype.Component;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
@Component
public class EmployeeMapperImpl implements Mapper<Employee, EmployeeDTO> {
    @Override
    public EmployeeDTO toDTO(Employee employee) {

        return EmployeeDTO.builder()
                .id(employee.getId())
                .surname(employee.getSurname())
                .name(employee.getName())
                .middleName(employee.getMiddleName())
                .phone(employee.getPhone())
                .isActive(employee.getIsActive())
                .profileId(employee.getProfileId().getId())
                .departmentId(employee.getDepartmentId().getId())
        .build();
    }

    @Override
    public Employee toModel(EmployeeDTO employeeDTO) {
        return Employee.builder()
                .id(employeeDTO.getId())
                .surname(employeeDTO.getSurname())
                .name(employeeDTO.getName())
                .middleName(employeeDTO.getMiddleName())
                .phone(employeeDTO.getPhone())
                .isActive(employeeDTO.getIsActive())
                .profileId(Profile.builder()
                        .id(employeeDTO.getProfileId())
                        .build())
                .departmentId(Department.builder()
                        .id(employeeDTO.getDepartmentId())
                        .build())
                .build();
    }
}
