package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.repository.DepartmentRepository;
import ru.metrovagonmash.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Override
    public Department save(Department model) {
        return departmentRepository.save(model);
    }

    @Override
    public Department update(Department model, Long id) {
        model.setId(id);
        return departmentRepository.save(model);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department deleteById(Long aLong) {
        return departmentRepository.findById(aLong)
                .orElseThrow(() -> new DepartmentException("Не найден ID"));
    }

    @Override
    public Department findById(Long aLong) {
        return departmentRepository.findById(aLong)
                .orElseThrow(() -> new DepartmentException("Не найден ID"));
    }
}
