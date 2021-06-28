package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Department;

@Repository
public interface DepartRepo extends JpaRepository<Department,Long> {
}
