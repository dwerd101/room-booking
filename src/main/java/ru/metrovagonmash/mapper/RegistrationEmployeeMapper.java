package ru.metrovagonmash.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.dto.RegistrationDTO;

@Mapper(componentModel = "spring")
public interface RegistrationEmployeeMapper extends MyMapper<Employee, RegistrationDTO> {
    @Override
    @Mappings({
            //@Mapping(target="id", source="employee.id"),
            @Mapping(target="surname", source="employee.surname"),
            @Mapping(target="name", source="employee.name"),
            @Mapping(target="middleName", source="employee.middleName"),
            @Mapping(target="profileId", source="employee.profileId.id"),
            @Mapping(target="departmentId", source="employee.departmentId.id"),
            @Mapping(target="phone", source="employee.phone"),
            @Mapping(target="email", source="employee.email")
    })
    RegistrationDTO toDTO(Employee employee);

    @Override
    @Mappings({
            //@Mapping(target="id", source="registrationDTO.id"),
            @Mapping(target="surname", source="registrationDTO.surname"),
            @Mapping(target="name", source="registrationDTO.name"),
            @Mapping(target="middleName", source="registrationDTO.middleName"),
            @Mapping(target="profileId.id", source="registrationDTO.profileId"),
            @Mapping(target="departmentId.id", source="registrationDTO.departmentId"),
            @Mapping(target="phone", source="registrationDTO.phone"),
            @Mapping(target="email", source="registrationDTO.email")
    })
    Employee toModel(RegistrationDTO registrationDTO);
}
