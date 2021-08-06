package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.repository.ProfileViewRepository;
import ru.metrovagonmash.repository.RecordTableViewRepository;
import ru.metrovagonmash.service.ProfileViewService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    final ProfileViewRepository profileViewRepository;
    private final ProfileViewService profileViewService;
    private final RecordTableViewRepository recordTableViewRepository;
    @GetMapping("/userpage")
    public String userPage(){
        return "userpage";
    }

    @GetMapping("/calendar")
    public String calendar(){
        return "calendar";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(ModelMap modelMap){
        List<ProfileView> list = profileViewRepository.findAll();
        modelMap.addAttribute("employeeList",list);
        return "adminpage";
    }

    @GetMapping("/admin/find-by-surname")
    public String getBySurnamePage(ModelMap modelMap) {
        List<ProfileView> list = null;
        modelMap.addAttribute("employeeList",list);
        return "adminpagefindemployee";
    }
    @PostMapping("/admin/find-by-surname/")
    public String findBySurnamePage(ModelMap modelMap,@RequestParam String findBySurname ) {
        List<ProfileView> list = profileViewRepository.findAllBySurname(findBySurname);
        if(list.isEmpty()) list = null;
        modelMap.addAttribute("employeeList",list);
        return "adminpagefindemployee";
    }


    @GetMapping("/admin/find-by-name")
    public String getByNamePage(ModelMap modelMap) {
        List<ProfileView> list = null;
        modelMap.addAttribute("employeeList",list);
        return "adminpagefindemployee";
    }
    @PostMapping("/admin/find-by-name/")
    public String findByNamePage(ModelMap modelMap,@RequestParam String findBySurname ) {
        List<ProfileView> list = profileViewRepository.findAllBySurname(findBySurname);
        if(list.isEmpty()) list = null;
        modelMap.addAttribute("employeeList",list);
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
        for (int i=0; i<idMas.length; i++) {
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

}
