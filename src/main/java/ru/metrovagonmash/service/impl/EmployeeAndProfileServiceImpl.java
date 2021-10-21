package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.service.EmployeeAndProfileService;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.service.ProfileService;

@Service
@RequiredArgsConstructor
public class EmployeeAndProfileServiceImpl implements EmployeeAndProfileService {
    private final EmployeeService employeeService;
    private final ProfileService profileService;

    @Override
    @Transactional
    public void update(EmployeeDTO employeeDTO, Profile profile) {
        employeeService.save(employeeDTO);
        profileService.save(profile);
    }

    @Override
    @Transactional
    public void deleteByProfileId(Long aLong) {
        employeeService.deleteById(findEmployeeByProfileId(aLong).getId());
        profileService.deleteById(aLong);
    }

    @Override
    public EmployeeDTO findEmployeeByProfileId(Long aLong) {
        return employeeService.findEmployeeByProfileId(aLong);
    }

    @Override
    public Profile findProfileById(Long aLong) {
        return profileService.findById(aLong);
    }


    @Override
    public EmployeeDTO findByLogin(String login) {
        return employeeService.findByLogin(login);
    }




}
