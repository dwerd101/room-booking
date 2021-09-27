package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.HistoryRecordTableEmployee;

@Repository
public interface HistoryRecordTableEmployeeRepository extends JpaRepository<HistoryRecordTableEmployee, Long> {

}
