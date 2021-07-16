package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;

@Repository
public interface RecordTableViewRepository extends JpaRepository<RecordTableView,Long> {
}
