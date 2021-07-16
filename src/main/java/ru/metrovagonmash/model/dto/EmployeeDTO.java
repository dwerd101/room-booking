package ru.metrovagonmash.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.Profile;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = "true")
public class EmployeeDTO {
    private Long id;
    private String surname;
    private String name;
    private String middleName;
    private String phone;
    private Boolean isActive;
    private Long profileId;
    private Long departmentId;


}
