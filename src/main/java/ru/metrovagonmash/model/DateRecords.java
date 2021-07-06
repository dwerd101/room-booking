/*
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
@Table(name = "date_record")
public class DateRecords extends AbstractBaseEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "number_room_id", referencedColumnName = "number_room")
    private VscRoom numberRoomId;

    @Column(name = "date_time_record")
    private LocalDateTime dateTimeRecord;

    @Override
    public Long getId() {
        return id;
    }
}
*/
