package ru.metrovagonmash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<RecordTable,Long> {
    @Query( nativeQuery = true,
            value = "select * from record_table where number_room_id=?1 and employee_id=?2")
    Optional<RecordTable> findByNumberRoomIdAndEmployeeId(Long roomNumberId, Long employeeId);
}
