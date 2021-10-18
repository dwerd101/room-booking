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
public class VscRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "number_room")
    private Long numberRoom;


    @Column(name = "is_active")
    private Boolean isActive;


}
