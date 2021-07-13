package ru.metrovagonmash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<RecordTable,Long> {
    @Query( nativeQuery = true,
            value = "select * from record_table where number_room_id=?1 and employee_id=?2")
    Optional<RecordTable> findByNumberRoomIdAndEmployeeId(Long roomNumberId, Long employeeId);

    @Query( nativeQuery = true, value = "select employee.name, employee.surname, employee.middle_name, record_table.record, vsc_room.is_active,  vsc_room.number_room\n" +
            "from record_table inner join employee  on record_table.employee_id = employee.id\n" +
            "join vsc_room  on record_table.number_room_id = vsc_room.id")
    List<RecordTable> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom();
}
