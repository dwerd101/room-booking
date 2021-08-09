package ru.metrovagonmash.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.service.EmployeeService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<EmployeeDTO>>> findAll() {
        return () -> ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<EmployeeDTO>> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return () -> ResponseEntity.ok(employeeService.save(employeeDTO));
    }

    @PostMapping("/update/{id}")
    public Callable<ResponseEntity<EmployeeDTO>> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable String id) {
        return () -> ResponseEntity.ok(employeeService.update(employeeDTO, Long.parseLong(id)));
    }

    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<EmployeeDTO>> deleteEmployee( @PathVariable String id) {
        return () -> ResponseEntity.ok(employeeService.deleteById(Long.parseLong(id)));
    }

}
