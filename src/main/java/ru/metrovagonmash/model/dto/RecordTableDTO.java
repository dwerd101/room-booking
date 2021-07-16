package ru.metrovagonmash.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.VscRoom;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = "true")
public class RecordTableDTO {
    private Long id;
    private String email;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime record;
    private Boolean isActive;
    private Long numberRoomId;

    private Long employeeId;
    private String employeeName, employeeSurname, employeeMiddleName;
    private String vcsRoomNumberRoom;

    //select employee.name, employee.surname, employee.middle_name, record_table.record, vsc_room.is_active,  vsc_room.number_room

}
