package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Slf4j
public class RegistrationController {
    private final RegistrationService registrationService;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;



    @GetMapping("/registration")
    public String registration(Model model) {
        log.info("Создание регистрации");
        model.addAttribute("userData", new RegistrationDTO());
        model.addAttribute("departamentService", departmentService);
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("userData")final @Valid RegistrationDTO registrationDTO, final BindingResult bindingResult, final Model model) {
        model.addAttribute("departamentService", departmentService);

        if(!registrationService.doesUserExist(registrationDTO)) {
            model.addAttribute("loginErrorMessage", false);
            registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            registrationDTO.setAccountNonLocked(true);
            registrationDTO.setIsActive(true);
            registrationDTO.setRole(Role.EMPLOYEE);

            registrationService.saveEmployeeAndProfile(registrationDTO);

            return "redirect:/";
        } else {
            model.addAttribute("loginErrorMessage", true);
            return "registration";
        }
    }

}
