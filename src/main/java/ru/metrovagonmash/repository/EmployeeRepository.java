package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository< Employee, Long> {

    @Query( nativeQuery = true,
            value = "select * from employee where department_id=?1 and profile_id=?2")
    Optional<Employee> findByDepartmentIdAndProfileId(Long depId, Long profId);
}