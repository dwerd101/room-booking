package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "record_table")
/*@SequenceGenerator(
        name = "seqid-gen",
        sequenceName = "SEQ_GEN", initialValue = 1, allocationSize = 1)*/
public class RecordTable {
    @Id
    private Long id;
    @Column(name = "email")
    private String email;

    @Column(name = "record")
    private LocalDateTime record;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "number_room_id", referencedColumnName = "id")
    private VscRoom numberRoomId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employeeId;

/*    @Override
    public Long getId() {
        return id;
    }*/
}
