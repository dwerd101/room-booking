package ru.metrovagonmash.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.service.EmployeeServiceImpl;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;
    @PostMapping("/save")
    public Callable<ResponseEntity<EmployeeDTO>> saveDepartment(@RequestBody EmployeeDTO employeeDTO) {
        return () -> ResponseEntity.ok(employeeService.save(employeeDTO));
    }
}
