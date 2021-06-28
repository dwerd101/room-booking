package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.Employee;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements RoomService<Employee, Long> {
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
        return null;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return null;
    }
}
