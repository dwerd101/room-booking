package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository< Employee, Long> {
}
