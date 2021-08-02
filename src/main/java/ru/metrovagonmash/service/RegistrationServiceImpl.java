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
public class RegistrationServiceImpl implements RoomService<RegistrationDTO, Long> {
    private final EmployeeRepository employeeRepository;
    private final ProfileRepository profileRepository;
    private final DepartmentRepository departmentRepository;
    private final MyMapper<Employee, RegistrationDTO> myEmployeeMapper;
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

    @Transactional
    public void saveEmployeeAndProfile(RegistrationDTO model) {
        myProfileMapper.toDTO(profileRepository.save(myProfileMapper.toModel(model)));
        myEmployeeMapper.toDTO(employeeRepository.save(toEmployee(model)));
    }

    //Заглушка
    private Employee toEmployee(RegistrationDTO model) {
        Employee employee = myEmployeeMapper.toModel(model);

        employee.setProfileId(profileRepository.findByLogin(model.getLogin())
                .orElseThrow(() -> new ProfileException("Профиль не найден")));

        employee.setDepartmentId(departmentRepository.findById(model.getDepartmentId())
                .orElseThrow(() -> new DepartmentException("Департамент не найден")));

        //Employee temp = employeeRepository.findByDepartmentIdAndProfileId(employee.getDepartmentId().getId(),
        //        employee.getProfileId().getId()).orElseThrow(() -> new EmployeeException("Не найден"));



        //Employee temp = employeeRepository.findByDepartmentId(employee.getDepartmentId()
        //        .getId()).orElseThrow(() -> new EmployeeException("Не найден"));

        return employee;
    }

    public boolean doesUserExist(RegistrationDTO model) {
        if (model != null)
            return profileRepository.findByLogin(model.getLogin()).isPresent();
        else return false;
    }
}
