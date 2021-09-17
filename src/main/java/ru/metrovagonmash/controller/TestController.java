package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.exception.EmployeeException;
import ru.metrovagonmash.mapper.EmployeeMyMapper;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.ProfileRepository;
import ru.metrovagonmash.repository.ProfileViewRepository;
import ru.metrovagonmash.repository.RecordTableRepository;
import ru.metrovagonmash.repository.ProfileViewSearchCriteriaRepostitory;
import ru.metrovagonmash.service.ProfileViewService;
import ru.metrovagonmash.service.VscRoomService;
import ru.metrovagonmash.service.mail.MailSenderService;
import ru.metrovagonmash.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final ProfileViewRepository profileViewRepository;
    private final ProfileViewService profileViewService;
    private final ProfileViewSearchCriteriaRepostitory recordTableViewSearchCriteriaRepostitory;
    private final RecordTableRepository recordTableRepository;
    private final EmployeeMyMapper employeeMyMapper;
    private final VscRoomService vscRoomService;
    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;


    // private final RecordTableViewRepository recordTableViewRepository;
    @GetMapping("/userpage")
    public String userPage() {
        return "userpage";
    }


    @GetMapping("/calendar/{idRoom}")
    public String calendar(@PathVariable String idRoom) {
        vscRoomService.findByNumberRoomIfNotFoundByNumberRoomThrowException(Long.parseLong(idRoom));
        return "calendar";
    }

    // FIXME: 30.08.2021 Сделать отправку сообщения с почты после того как пользователь сделал запись
    // FIXME: 30.08.2021 Сделать возможность изменения личных данных пользователя - частично добавили
    // FIXME: 13.09.2021 Сброс пароля добавить - частисно добавили
    @GetMapping("/get-user")
    @ResponseBody
    public Callable<ResponseEntity<EmployeeDTO>> getEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        EmployeeDTO employeeDTO = employeeMyMapper.toDTO(recordTableRepository.findByLogin(user.getUsername()).orElseThrow(() -> new EmployeeException())
                .getEmployeeId());
        return () -> ResponseEntity.ok(employeeDTO);
    }

    @PostMapping("/forget-password/send")
    public String forgetPassword(@RequestParam(value = "username") String username, ModelMap modelMap) {
        Optional<Profile> profileOptional = profileRepository.findByLogin(username);
        if(profileOptional.isPresent()) {
            mailSenderService.send("79154472780@yandex.ru", "Forget password", "You forgot password");
            return "succesfulSendEmailForgetPassword";
        }
        else {
            modelMap.addAttribute("error", true);
            return "forgetpassword";
        }

    }

     @GetMapping("/forget-password")
     public String forgetPassword() {
         return "forgetpassword";
     }

   /* @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }*/

    @GetMapping("/source")
    public String search(@RequestParam(value = "search", required = false) String search, ModelMap modelMap) {
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        List<ProfileView> list = recordTableViewSearchCriteriaRepostitory.searchProfile(params);
        modelMap.addAttribute("employeeList", list);
        return "adminpage";
    }


    @GetMapping("/admin")
    public String admin(ModelMap modelMap) {
        List<ProfileView> list = profileViewService.findAll();
        modelMap.addAttribute("employeeList", list);
        return "adminpage";
    }

    @GetMapping("/admin/find-by-surname")
    public String getBySurnamePage(ModelMap modelMap) {
        List<ProfileView> list = null;
        modelMap.addAttribute("employeeList", list);
        return "adminpagefindemployee";
    }

    @PostMapping("/admin/find-by-surname/")
    public String findBySurnamePage(ModelMap modelMap, @RequestParam String findBySurname) {
        List<ProfileView> list = profileViewRepository.findAllBySurname(findBySurname);
        if (list.isEmpty()) list = null;
        modelMap.addAttribute("employeeList", list);
        return "adminpagefindemployee";
    }


    @GetMapping("/admin/find-by-name")
    public String getByNamePage(ModelMap modelMap) {
        List<ProfileView> list = null;
        modelMap.addAttribute("employeeList", list);
        return "adminpagefindemployee";
    }

    @PostMapping("/admin/find-by-name/")
    public String findByNamePage(ModelMap modelMap, @RequestParam String findBySurname) {
        List<ProfileView> list = profileViewRepository.findAllBySurname(findBySurname);
        if (list.isEmpty()) list = null;
        modelMap.addAttribute("employeeList", list);
        return "adminpagefindemployee";
    }

    @PostMapping("/save-user")
    public String updateUsers(@RequestParam(name = "id") String id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "surname") String surname,
                              @RequestParam(name = "middleName") String middleName,
                              @RequestParam(name = "phone") String phone,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "banned") String banned
    ) {
        String[] idMas = id.split(",");
        String[] nameMas = name.split(",");
        String[] surnameMas = surname.split(",");
        String[] middleNameMas = middleName.split(",");
        String[] phoneMas = phone.split(",");
        String[] emailMas = email.split(",");
        String[] bannedMas = banned.split(",");
        List<ProfileView> profileViewList = new ArrayList<>();
        for (int i = 0; i < idMas.length; i++) {
            profileViewList.add(
                    ProfileView.builder()
                            .id(Long.parseLong(idMas[i]))
                            .name(nameMas[i])
                            .surname(surnameMas[i])
                            .middleName(middleNameMas[i])
                            .phone(phoneMas[i])
                            .email(emailMas[i])
                            .banned(Boolean.parseBoolean(bannedMas[i]))
                            .build()
            );
        }
        profileViewService.batchUpdateProfileAndEmployee(profileViewList);

        return "redirect:/admin";
    }

    @GetMapping("/admin/find-by-param")
    public String getByParamPage(ModelMap modelMap) {
        List<ProfileView> list = null;
        modelMap.addAttribute("profileView", new ProfileView());
        modelMap.addAttribute("employeeList", list);
        return "adminpagefullfindemployee";
    }

    @PostMapping("/admin/find-by-param")
    public String findByParamPage(@ModelAttribute("profileView") final ProfileView profileView) {

        return "adminpagefullfindemployee";
    }


}
