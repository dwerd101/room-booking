package ru.metrovagonmash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;

@Service
@RequiredArgsConstructor
public class EmployeeAndProfileServiceImpl implements EmployeeAndProfileService {
    private final EmployeeService employeeService;
    private final ProfileService profileService;

    @Override
    @Transactional
    public void update(EmployeeDTO employeeDTO, Profile profile) {
        EmployeeDTO tempEmployee = employeeService.findById(employeeDTO.getId());
        Profile tempProfile = profileService.findById(profile.getId());
        employeeDTO.setIsActive(tempEmployee.getIsActive());
        //profile.setIsActive(tempProfile.getIsActive());
        profile.setPassword(tempProfile.getPassword());

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



}
