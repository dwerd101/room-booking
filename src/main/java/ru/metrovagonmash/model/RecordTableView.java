package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


// переделать потом hibernate. Используется заглушка прописанная в скрипте schema.sql
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "record_table_view")
@Immutable
@Subselect("select record_table.id, record_table.email, record_table.employee_id, employee.name, employee.surname, employee.middle_name, record_table.title, record_table.start_event, record_table.end_event, record_table.is_active, vsc_room.number_room\n" +
        "from record_table inner join employee on record_table.employee_id = employee.id\n" +
        "join vsc_room  on record_table.number_room_id = vsc_room.id")
public class RecordTableView {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name ="email")
    private String email;
    @Column(name ="employee_id")
    private Long employeeId;
    @Column(name ="name")
    private String employeeName;
    @Column(name ="surname")
    private String employeeSurname;
    @Column(name ="middle_name")
    private String employeeMiddleName;
    @Column(name ="number_room")
    private String vcsRoomNumberRoom;
    @Column(name ="is_active")
    private Boolean isActive;
    @Column(name = "title")
    private String title;
    @Column(name = "start_event")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startEvent;
    @Column(name = "end_event")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime  endEvent;
}
