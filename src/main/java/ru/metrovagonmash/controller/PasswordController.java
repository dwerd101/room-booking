package ru.metrovagonmash.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.metrovagonmash.exception.ProfileNotFoundException;
import ru.metrovagonmash.model.PasswordConfirmationToken;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.*;
import ru.metrovagonmash.service.*;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class PasswordController {
    private final ProfileService profileService;
    private final MailSenderService mailSenderService;
    private final PasswordConfirmationTokenService passwordConfirmationTokenService;
    private final EmployeeService employeeService;

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "forgetpassword";
    }

    @PostMapping("/forget-password/send")
    public String forgetPassword(@RequestParam(value = "username") String username, ModelMap modelMap) {
        Profile profile = profileService.findByLogin(username);

        if(profile != null) {
            PasswordConfirmationToken passwordConfirmationToken = PasswordConfirmationToken.builder()
                    .profileId(profile)
                    .token(UUID.randomUUID().toString())
                    .build();

            passwordConfirmationTokenService.save(passwordConfirmationToken);
            String email = employeeService.findByProfileID(profile.getId()).getEmail();

            mailSenderService.send(email, "Forget password", "You forgot password" +
                    " link: " + "http://localhost:8080/reset-password?token=" + passwordConfirmationToken.getToken());
            return "succesfulSendEmailForgetPassword";
        }
        else {
            modelMap.addAttribute("error", true);
            return "forgetpassword";
        }

    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String confirmationToken, ModelMap modelMap) {
        PasswordConfirmationToken passwordConfirmationToken = passwordConfirmationTokenService.findByToken(confirmationToken);
        if (passwordConfirmationToken != null) {
            Profile profile = profileService.findById(passwordConfirmationToken.getProfileId().getId());
            modelMap.addAttribute("profileData", profile);
            passwordConfirmationTokenService.deleteById(passwordConfirmationToken.getId());
        }
        else {
            modelMap.addAttribute("error", true);
        }
        return "resetpassword";
    }

    @PostMapping("reset-password")
    public String saveNewPassword(@ModelAttribute("profileData") Profile newProfileData) {
        Profile profile = profileService.findByLogin(newProfileData.getLogin());
        profile.setPassword(passwordEncoder(newProfileData.getPassword()));
        profileService.save(profile);
        return "succesfulSendEmailForgetPassword";
    }

    private String passwordEncoder(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
        return passwordEncoder.encode(password);
    }
}
