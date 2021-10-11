package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.EmployeeBadRequestException;
import ru.metrovagonmash.mapper.MyMapper;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.EmployeeRepository;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.service.ProfileService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MyMapper<Employee, EmployeeDTO> myMapper;


    @Override
    public EmployeeDTO save(EmployeeDTO model) {
        return myMapper.toDTO(employeeRepository.save(myMapper.toModel(model)));
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
        EmployeeDTO employeeDTO = myMapper.toDTO(employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeBadRequestException("Не найден ID")));
        employeeRepository.deleteById(aLong);
        return employeeDTO;
    }

    @Override
    public EmployeeDTO findByProfileID(Long profileID) {
        return myMapper.toDTO(employeeRepository.findByProfileId(profileID)
                .orElseThrow(() -> new EmployeeBadRequestException("Не найден ID")));
    }

    @Override
    public EmployeeDTO findByLogin(String login) {
        return myMapper.toDTO(employeeRepository.findByLogin(login)
                .orElseThrow(() -> new EmployeeBadRequestException("Не найден логин")));
    }

    @Override
    public Boolean isPresentByDepartmentId(Long aLong) {
        return !employeeRepository.findAllByDepartmentId(aLong).isEmpty();
    }

    @Override
    public EmployeeDTO findById(Long aLong) {
        return myMapper.toDTO(employeeRepository.findById(aLong)
                .orElseThrow(() -> new EmployeeBadRequestException("Не найден ID")));
    }

    @Override
    public EmployeeDTO findEmployeeByProfileId(Long aLong) {
        return myMapper.toDTO(employeeRepository.findByProfileId(aLong)
                .orElseThrow(() -> new EmployeeBadRequestException("Не найден ID")));
    }
}
