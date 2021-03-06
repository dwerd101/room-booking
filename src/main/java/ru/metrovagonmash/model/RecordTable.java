
package ru.metrovagonmash.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "record_table")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordTable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "number_room_id", referencedColumnName = "id")
    private VscRoom numberRoomId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employeeId;

    @Override
    public int hashCode() {
        return 13;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HistoryRecordTableEmployee other = (HistoryRecordTableEmployee) obj;
        if (id == null) {
            return false;
        } else return id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "RecordTable{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", title='" + title + '\'' +
            ", startEvent=" + startEvent +
            ", endEvent=" + endEvent +
            ", isActive=" + isActive +
            '}';
    }
}