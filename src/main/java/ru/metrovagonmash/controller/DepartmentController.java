package ru.metrovagonmash.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.service.DepartmentService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<Department>>> findAll() {
        return () -> ResponseEntity.ok(departmentService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<Department>> saveDepartment(@RequestBody Department department) {
        return () -> ResponseEntity.ok(departmentService.save(department));
    }

    @PutMapping("/update/{id}")
    public Callable<ResponseEntity<Department>> updateDepartment(@RequestBody Department department, @PathVariable String id) {
        return () -> ResponseEntity.ok(departmentService.update(department,Long.parseLong(id)));
    }
    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<Department>> deleteDepartment( @PathVariable String id) {
        return () -> ResponseEntity.ok(departmentService.deleteById(Long.parseLong(id)));
    }
}
