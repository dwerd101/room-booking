package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements RoomService<Department, Long> {
    private final DepartmentRepository departmentRepository;
    @Override
    public Department save(Department model) {
        return null;
    }

    @Override
    public Department update(Department model) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department deleteById(Long aLong) {
        return null;
    }
}
