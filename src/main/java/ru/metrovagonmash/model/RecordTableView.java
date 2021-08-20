package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


// переделать потом hibernate. Используется заглушка прописанная в скрипте schema.sql
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "record_table_view")
@Immutable
public class RecordTableView {
    @Id
    @Column(name = "id")
    private Long id;
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
    private LocalDateTime startEvent;
    @Column(name = "end_event")
    private LocalDateTime endEvent;
}
