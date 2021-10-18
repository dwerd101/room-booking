package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "history_record_table_employee")

public class HistoryRecordTableEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "start_event")
    private ZonedDateTime startEvent;

    @Column(name = "end_event")
    private ZonedDateTime  endEvent;

    @Column(name = "is_active")
    private Boolean isActive;

    private Long numberRoomId;

    private Long employeeId;

}
