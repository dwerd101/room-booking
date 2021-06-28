package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.service.DepartmentServiceImpl;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<Department>>> findAll() {
        return () -> ResponseEntity.ok(departmentService.findAll());
    }
}
