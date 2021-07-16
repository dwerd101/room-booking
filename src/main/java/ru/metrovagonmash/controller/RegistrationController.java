package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.metrovagonmash.config.security.Role;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.service.ProfileServiceImpl;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
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
}
