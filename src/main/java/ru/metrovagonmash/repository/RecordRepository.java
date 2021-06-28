package ru.metrovagonmash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.RecordTable;

@Repository
public interface RecordRepository extends JpaRepository<RecordTable,Long> {
}
