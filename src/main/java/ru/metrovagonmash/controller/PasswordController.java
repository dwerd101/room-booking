package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.metrovagonmash.exception.ProfileNotFoundException;
import ru.metrovagonmash.model.PasswordConfirmationToken;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.service.*;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:password-text.properties")
public class PasswordController {
    @Value("${reset.url}")
    private String resetPasswordUrl;
    private final ProfileService profileService;
    private final MailSenderService mailSenderService;
    private final PasswordConfirmationTokenService passwordConfirmationTokenService;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "forgetpassword";
    }

    @PostMapping("/forget-password/send")
    public String forgetPassword(@RequestParam(value = "username") String username, ModelMap modelMap) {


        try {
            Profile profile = profileService.findByLogin(username);
            String email = employeeService.findByProfileID(profile.getId()).getEmail();

            mailSenderService.send(email, "Forget password", "You forgot password" +
                    " link: " + resetPasswordUrl + saveToken(profile).getToken());
            return "succesfulSendEmailForgetPassword";
        }
        catch (ProfileNotFoundException e){
            modelMap.addAttribute("error", true);
            return "forgetpassword";
        }

    }

    private PasswordConfirmationToken saveToken (Profile profile) {
        PasswordConfirmationToken passwordConfirmationToken = PasswordConfirmationToken.builder()
                .profileId(profile)
                .token(UUID.randomUUID().toString())
                .build();
        return passwordConfirmationTokenService.save(passwordConfirmationToken);
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String confirmationToken, ModelMap modelMap) {

        try {
            PasswordConfirmationToken passwordConfirmationToken = passwordConfirmationTokenService.findByToken(confirmationToken);
            Profile profile = profileService.findById(passwordConfirmationToken.getProfileId().getId());
            modelMap.addAttribute("profileData", profile);
            passwordConfirmationTokenService.deleteById(passwordConfirmationToken.getId());
        }
        catch (NoSuchElementException e){
            modelMap.addAttribute("error", true);
        }
        return "resetpassword";
    }

    @PostMapping("reset-password")
    public String saveNewPassword(@ModelAttribute("profileData") Profile newProfileData) {
        Profile profile = profileService.findByLogin(newProfileData.getLogin());
        profile.setPassword(passwordEncoder.encode(newProfileData.getPassword()));
        profileService.save(profile);
        return "succesfulSendEmailForgetPassword";
    }
}
