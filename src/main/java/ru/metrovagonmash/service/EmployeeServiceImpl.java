package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.repository.EmployeeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements RoomService<Employee, Long> {
    private final EmployeeRepository employeeRepository;
    @Override
    public Employee save(Employee model) {
        return null;
    }

    @Override
    public Employee update(Employee model) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee deleteById(Long aLong) {
        return null;
    }
}
