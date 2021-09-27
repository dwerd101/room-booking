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

// FIXME: 27.09.2021 Постараться использовать реализию через hibernate
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

   // @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  //  @JoinColumn(name = "number_room_id", referencedColumnName = "id")
    private Long numberRoomId;

    // проверить работоспособность
   // @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   // @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Long employeeId;

}
