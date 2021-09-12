package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeAndProfileService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EmployeeAdminController {
    private final EmployeeAndProfileService employeeAndProfileService;
    private final DepartmentService departmentService;

    @GetMapping("/employee/edit/{id}")
    public String editEmployee(@PathVariable String id, ModelMap modelMap) {
        EmployeeDTO employeeDTO = employeeAndProfileService.findEmployeeByProfileId(Long.parseLong(id));
        Profile profile = employeeAndProfileService.findProfileById(Long.parseLong(id));
        modelMap.addAttribute("employeeData", employeeDTO);
        modelMap.addAttribute("profileData", profile);
        modelMap.addAttribute("departmentData", departmentService.findAll());
        return "editemployee";
    }

    @PostMapping("/employee/edit/{id}")
    public String saveEmployee(@PathVariable String id,
                               @ModelAttribute("employeeData")final @Valid EmployeeDTO employeeDTO,
                               @ModelAttribute("profileData")final @Valid Profile profile) {
        profile.setId(Long.parseLong(id));
        employeeDTO.setId(employeeAndProfileService.findEmployeeByProfileId(Long.parseLong(id)).getId());
        employeeDTO.setProfileId(Long.parseLong(id));
        employeeAndProfileService.update(employeeDTO, profile);
        return "redirect:/admin/find-by-param";
    }

    @GetMapping("/employee/delete/{id}")
    public String askDeleteEmployee(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("profileId", id);
        return "deleteemployee";
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeAndProfileService.deleteByProfileId(Long.parseLong(id));
        return "redirect:/admin/find-by-param";
    }
}
