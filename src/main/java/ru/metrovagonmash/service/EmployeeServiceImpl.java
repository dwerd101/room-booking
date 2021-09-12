package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.EmployeeException;
import ru.metrovagonmash.mapper.MyMapper;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MyMapper<Employee, EmployeeDTO> myMapper;
    private final DepartmentService departmentService;
    private final ProfileService profileService;


    @Override
    public EmployeeDTO save(EmployeeDTO model) {
        return myMapper.toDTO(employeeRepository.save(toEmployee(model)));
    }

    @Override
    public EmployeeDTO update(EmployeeDTO model, Long aLong) {
        model.setId(aLong);
        return myMapper.toDTO(employeeRepository.save(myMapper.toModel(model)));
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(myMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO deleteById(Long aLong) {
        EmployeeDTO employeeDTO = myMapper.toDTO( employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeException("Не найден ID")));
        employeeRepository.deleteById(aLong);
        return employeeDTO;
    }

    // изменить заглушку на будущее
    private Employee toEmployee(EmployeeDTO model) {
        Employee employee = myMapper.toModel(model);
        //Employee temp = employeeRepository.findByDepartmentIdAndProfileId(employee.getDepartmentId().getId(),
        //        employee.getProfileId().getId()).orElseThrow(() -> new EmployeeException("Не найден"));

        employee.setDepartmentId(departmentService.findById(model.getDepartmentId()));


        employee.setProfileId(profileService.findById(model.getProfileId()));
        return employee;
    }

    @Override
    public boolean isPresentByDepartmentId(Long aLong) {
        return !employeeRepository.findAllByDepartmentId(aLong).isEmpty();
    }

    @Override
    public EmployeeDTO findEmployeeByProfileId(Long aLong) {
        return myMapper.toDTO(employeeRepository.findByProfileId(aLong)
                .orElseThrow(() -> new EmployeeException("Не найден ID")));
    }

    @Override
    public EmployeeDTO findById(Long aLong) {
        return myMapper.toDTO(employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeException("Не найден ID")));
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
