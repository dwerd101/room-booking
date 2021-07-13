package ru.metrovagonmash.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.VscRoom;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordTableDTO {
    private Long id;
    private String email;
    private LocalDateTime record;
    private Boolean isActive;
    private Long numberRoomId;
    private Long employeeId;
}
