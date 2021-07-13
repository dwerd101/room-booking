package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.EmployeeException;
import ru.metrovagonmash.mapper.Mapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements RoomService<EmployeeDTO, Long> {
    private final EmployeeRepository employeeRepository;
    private final Mapper<Employee, EmployeeDTO> mapper;


    @Override
    public EmployeeDTO save(EmployeeDTO model) {
        return mapper.toDTO(employeeRepository.save(toEmployee(model)));
    }

    @Override
    public EmployeeDTO update(EmployeeDTO model, Long aLong) {
        model.setId(aLong);
        return mapper.toDTO(employeeRepository.save(mapper.toModel(model)));
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO deleteById(Long aLong) {
        return mapper.toDTO( employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeException("Не найден ID")));
    }

    // изменить заглушку на будущее
    private Employee toEmployee(EmployeeDTO model) {
        Employee employee = mapper.toModel(model);
        Employee temp = employeeRepository.findByDepartmentIdAndProfileId(employee.getDepartmentId().getId(),
                employee.getProfileId().getId()).orElseThrow(() -> new EmployeeException("Не найден"));
        employee.setDepartmentId(temp.getDepartmentId());
        employee.setProfileId(temp.getProfileId());
        return employee;
    }
  /*  @Override
    public Employee save(Employee model) {
        return employeeRepository.save(model);
    }

    @Override
    public Employee update(Employee model, Long id) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee deleteById(Long aLong) {
        return employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeException("Не найден ID"));
    }
*/
}
