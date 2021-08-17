package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.metrovagonmash.config.security.Role;

import ru.metrovagonmash.model.dto.RegistrationDTO;
import ru.metrovagonmash.service.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    private final DepartmentService departmentService;
    //public boolean loginErrorMessage;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userData", new RegistrationDTO());
        model.addAttribute("departamentService", departmentService);
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("userData")final @Valid RegistrationDTO registrationDTO, final BindingResult bindingResult, final Model model) {
        model.addAttribute("departamentService", departmentService);

        /*if (bindingResult.hasErrors()) {
            return "registration";
        }*/

        if(!registrationService.doesUserExist(registrationDTO)) {
            //loginErrorMessage = false;
            model.addAttribute("loginErrorMessage", false);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
            String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
            registrationDTO.setPassword(encodedPassword);

            registrationDTO.setAccountNonLocked(true);
            registrationDTO.setIsActive(true);
            registrationDTO.setRole(Role.EMPLOYEE);

            registrationService.saveEmployeeAndProfile(registrationDTO);

            return "redirect:/";
        } else {
            //loginErrorMessage = true;
            model.addAttribute("loginErrorMessage", true);
            return "registration";
        }
    }

    /*
    private final ProfileServiceImpl profileService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userData", new Profile());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("userData")final @Valid Profile profile, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,12);
        String encodedPassword = passwordEncoder.encode(profile.getPassword());
        profile.setPassword(encodedPassword);

        profile.setAccountNonLocked(true);
        profile.setIsActive(true);
        profile.setRole(Role.EMPLOYEE);
        profileService.save(profile);
        return "redirect:/";
    }
    */
}
