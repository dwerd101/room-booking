package ru.metrovagonmash.controller.user.edit;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeAndProfileService;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserEditController {

    private final EmployeeAndProfileService employeeAndProfileService;
    private final DepartmentService departmentService;

    @GetMapping("/user/edit")
    public String editEmployee(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        EmployeeDTO employeeDTO = employeeAndProfileService.findByLogin(user.getUsername());
        Profile profile = employeeAndProfileService.findProfileById(employeeDTO.getProfileId());
        modelMap.addAttribute("employeeData", employeeDTO);
        modelMap.addAttribute("profileData", profile);
        modelMap.addAttribute("departmentData", departmentService.findAll());
        return "edituserpage";
    }

    @PostMapping("/user")
    public String saveEmployee(@ModelAttribute("employeeData")final @Valid EmployeeDTO employeeDTO,
                               @ModelAttribute("profileData")final @Valid Profile profile) {

        EmployeeDTO tempEmployeeDTO = employeeAndProfileService.findByLogin(profile.getLogin());
        Profile tempProfile = employeeAndProfileService.findProfileById(tempEmployeeDTO.getProfileId());
        profile.setId(tempProfile.getId());
        profile.setPassword(tempProfile.getPassword());
        employeeDTO.setIsActive(tempEmployeeDTO.getIsActive());
        employeeDTO.setId(tempEmployeeDTO.getId());
        employeeDTO.setProfileId(tempProfile.getId());
        employeeAndProfileService.update(employeeDTO, profile);
        return "redirect:/user";
    }
}

