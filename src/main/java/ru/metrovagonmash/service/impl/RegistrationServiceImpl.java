package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.mapper.VCMapper;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.model.dto.RegistrationDTO;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.service.ProfileService;
import ru.metrovagonmash.service.RegistrationService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final EmployeeService employeeService;
    private final ProfileService profileService;
    private final DepartmentService departmentService;
    private final VCMapper<EmployeeDTO, RegistrationDTO> myEmployeeMapper;
    private final VCMapper<Profile, RegistrationDTO> myProfileMapper;


    @Override
    @Transactional
    public void saveEmployeeAndProfile(RegistrationDTO model) {
        myProfileMapper.toDTO(profileService.save(myProfileMapper.toModel(model)));
        myEmployeeMapper.toDTO(employeeService.save(toEmployee(model)));
    }

    //Заглушка
    private EmployeeDTO toEmployee(RegistrationDTO model) {
        EmployeeDTO employeeDTO = myEmployeeMapper.toModel(model);

        employeeDTO.setProfileId(profileService.findByLogin(model.getLogin()).getId());

        employeeDTO.setDepartmentId(departmentService.findById(model.getDepartmentId()).getId());

        return employeeDTO;
    }

    @Override
    public boolean doesUserExist(RegistrationDTO model) {
        if (model != null)
            return profileService.doesProfileExist(model.getLogin());
        else return false;
    }
}
