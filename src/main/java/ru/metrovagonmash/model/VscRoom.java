package ru.metrovagonmash.model;
import lombok.*;

import javax.persistence.*;

//@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vsc_room")
/*@SequenceGenerator(
        name = "seqid-gen",
        sequenceName = "SEQ_GEN", initialValue = 1, allocationSize = 1)*/
public class VscRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "number_room")
    private Long numberRoom;

 /*   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_time_records_id",referencedColumnName = "id")
    private RecordTable dateTimeRecordsId;*/

    @Column(name = "is_active")
    private Boolean isActive;


}
