package ru.metrovagonmash.service;

import ru.metrovagonmash.model.Department;

public interface DepartmentService extends RoomService<Department, Long> {
    Department findById(Long aLong);
}
