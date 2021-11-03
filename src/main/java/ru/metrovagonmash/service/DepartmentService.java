package ru.metrovagonmash.service;

import ru.metrovagonmash.model.Department;

import java.util.List;

public interface DepartmentService extends RoomServiceCRUD<Department, Long> {
    Department findById(Long aLong);
    void batchUpdateDepartment(List<Department> departmentList);
}
