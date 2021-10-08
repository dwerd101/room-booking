package ru.metrovagonmash.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.ProfileViewSearchCriteriaRepostitory;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeAndProfileService;
import ru.metrovagonmash.service.ProfileViewService;
import ru.metrovagonmash.specification.SearchCriteria;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/employees")
public class EmployeeAdminController {
    private final EmployeeAndProfileService employeeAndProfileService;
    private final DepartmentService departmentService;
    private final ProfileViewService profileViewService;
    private final ProfileViewSearchCriteriaRepostitory profileViewSearchCriteriaRepostitory;

    @PostMapping("/save")
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

        return "redirect:/admin/employees/";
    }


    @GetMapping("/")
    public String getByParamPage(@RequestParam(value = "search", required = false) String search,
                                 ModelMap modelMap) {
        List<ProfileView> list;
        if (search != null) {
            List<SearchCriteria> params = new ArrayList<>();
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }

            list = profileViewSearchCriteriaRepostitory.search(params);
        }
        else {
            list = profileViewService.findAll();
        }

        modelMap.addAttribute("employeeList",list);
        modelMap.addAttribute("findProfileView", new ProfileView());
        return "adminpagefullfindemployee";
    }



    @PostMapping("/")
    public String findByParamPage(//@RequestParam(name = "findById") String findById,
                                  //@RequestParam(name = "findByName") String findByName,
                                  //@RequestParam(name = "findBySurname") String findBySurname,
                                  //@RequestParam(name = "findByMiddleName") String findByMiddleName,
                                  //@RequestParam(name = "findByPhone") String findByPhone,
                                  //@RequestParam(name = "findByEmail") String findByEmail,
                                  //@RequestParam(name = "findByBanned") String findByBanned,
                                  @ModelAttribute("findProfileView") ProfileView profileView,
                                  ModelMap modelMap) {

        List<SearchCriteria> params = new ArrayList<>();

        /*
        if (!findById.isEmpty())
            params.add(new SearchCriteria("id",":",Long.valueOf(findById)));
        if (!findByName.isEmpty())
            params.add(new SearchCriteria("name",":",findByName));
        if (!findBySurname.isEmpty())
            params.add(new SearchCriteria("surname",":",findBySurname));
        if (!findByMiddleName.isEmpty())
            params.add(new SearchCriteria("middleName",":",findByMiddleName));
        if (!findByPhone.isEmpty())
            params.add(new SearchCriteria("phone",":",findByPhone));
        if (!findByEmail.isEmpty())
            params.add(new SearchCriteria("email",":",findByEmail));
        if (!findByBanned.isEmpty())
            params.add(new SearchCriteria("banned",":",Boolean.valueOf(findByBanned)));

         */

        if (profileView.getId() != null)
            params.add(new SearchCriteria("id",":",profileView.getId()));
        if (profileView.getName() != null)
            params.add(new SearchCriteria("name",":",profileView.getName()));
        if (profileView.getSurname() != null)
            params.add(new SearchCriteria("surname",":",profileView.getSurname()));
        if (profileView.getMiddleName() != null)
            params.add(new SearchCriteria("middleName",":",profileView.getMiddleName()));
        if (profileView.getPhone() != null)
            params.add(new SearchCriteria("phone",":",profileView.getPhone()));
        if (profileView.getEmail() != null)
            params.add(new SearchCriteria("email",":",profileView.getEmail()));
        if (profileView.getBanned() != null)
            params.add(new SearchCriteria("banned",":",profileView.getBanned()));



        List<ProfileView> list = profileViewSearchCriteriaRepostitory.search(params);
        modelMap.addAttribute("employeeList",list);
        return "adminpagefullfindemployee";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, ModelMap modelMap) {
        EmployeeDTO employeeDTO = employeeAndProfileService.findEmployeeByProfileId(Long.parseLong(id));
        Profile profile = employeeAndProfileService.findProfileById(Long.parseLong(id));
        modelMap.addAttribute("employeeData", employeeDTO);
        modelMap.addAttribute("profileData", profile);
        modelMap.addAttribute("departmentData", departmentService.findAll());
        return "editemployee";
    }

    @PostMapping("/edit/{id}")
    public String saveEmployee(@PathVariable String id,
                               @ModelAttribute("employeeData")final @Valid EmployeeDTO employeeDTO,
                               @ModelAttribute("profileData")final @Valid Profile profile) {
        EmployeeDTO tempEmployeeDTO = employeeAndProfileService.findEmployeeByProfileId(Long.parseLong(id));
        Profile tempProfile = employeeAndProfileService.findProfileById(Long.parseLong(id));
        profile.setId(Long.parseLong(id));
        profile.setPassword(tempProfile.getPassword());
        employeeDTO.setIsActive(tempEmployeeDTO.getIsActive());
        employeeDTO.setId(tempEmployeeDTO.getId());
        employeeDTO.setProfileId(Long.parseLong(id));
        employeeAndProfileService.update(employeeDTO, profile);
        return "redirect:/admin/employees/";
    }

    @GetMapping("/delete/{id}")
    public String askDeleteEmployee(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("profileId", id);
        return "deleteemployee";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeAndProfileService.deleteByProfileId(Long.parseLong(id));
        return "redirect:/admin/employees/";
    }
}
