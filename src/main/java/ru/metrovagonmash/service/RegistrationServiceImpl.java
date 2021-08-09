package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.exception.DepartmentException;
import ru.metrovagonmash.exception.EmployeeException;
import ru.metrovagonmash.exception.ProfileException;
import ru.metrovagonmash.mapper.MyMapper;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.model.dto.RegistrationDTO;
import ru.metrovagonmash.repository.DepartmentRepository;
import ru.metrovagonmash.repository.EmployeeRepository;
import ru.metrovagonmash.repository.ProfileRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService  {

    private final EmployeeService employeeService;
    private final ProfileService profileService;
    private final DepartmentService departmentService;
    private final MyMapper<EmployeeDTO, RegistrationDTO> myEmployeeMapper;
    private final MyMapper<Profile, RegistrationDTO> myProfileMapper;

    @Override
    public RegistrationDTO save(RegistrationDTO model) {
        return null;
    }

    @Override
    public RegistrationDTO update(RegistrationDTO model, Long aLong) {
        return null;
    }

    @Override
    public List<RegistrationDTO> findAll() {
        return null;
    }

    @Override
    public RegistrationDTO deleteById(Long aLong) {
        return null;
    }

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

        //Employee temp = employeeRepository.findByDepartmentIdAndProfileId(employee.getDepartmentId().getId(),
        //        employee.getProfileId().getId()).orElseThrow(() -> new EmployeeException("Не найден"));



        //Employee temp = employeeRepository.findByDepartmentId(employee.getDepartmentId()
        //        .getId()).orElseThrow(() -> new EmployeeException("Не найден"));

        return employeeDTO;
    }

    @Override
    public boolean doesUserExist(RegistrationDTO model) {
        if (model != null)
            return profileService.doesProfileExist(model.getLogin());
        else return false;
    }
}
