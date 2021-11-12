package ru.metrovagonmash.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.config.search.SearchByURLParams;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.search.ProfileViewSearchCriteriaRepositoryImpl;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeAndProfileService;
import ru.metrovagonmash.service.impl.ProfileViewServiceImpl;
import ru.metrovagonmash.specification.SearchCriteria;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/employees")
public class EmployeeAdminController {
    private final EmployeeAndProfileService employeeAndProfileService;
    private final DepartmentService departmentService;
    private final ProfileViewServiceImpl profileViewService;
    private final ProfileViewSearchCriteriaRepositoryImpl profileViewSearchCriteriaRepository;
    private final SearchByURLParams searchByURLParams;

    @PostMapping("/save")
    public String updateUsers(@RequestParam(name = "id") String id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "surname") String surname,
                              @RequestParam(name = "middleName") String middleName,
                              @RequestParam(name = "phone") String phone,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "banned") String banned
    ) {
        profileViewService.batchUpdateProfileAndEmployee(getProfileViewListFromParams(id, name, surname, middleName,
                phone, email, banned));

        return "redirect:/admin/employees/";
    }

    @GetMapping("/")
    public String getByParamPage(@RequestParam(value = "search", required = false) String search,
                                 ModelMap modelMap) {
        List<ProfileView> list;
        if (search != null) {
            list = profileViewSearchCriteriaRepository.search(searchByURLParams.getParamsFromSearch(search));
        }
        else {
            list = profileViewService.findAll();
        }

        modelMap.addAttribute("employeeList",list);
        modelMap.addAttribute("findProfileView", new ProfileView());
        return "adminpagefullfindemployee";
    }

    @PostMapping("/")
    public String findByParamPage(@ModelAttribute("findProfileView") ProfileView profileView,
                                  ModelMap modelMap) {
        List<ProfileView> list = profileViewSearchCriteriaRepository.search(getParamsFromProfileView(profileView));
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
        saveEmployeeFromPageData(Long.parseLong(id), employeeDTO, profile);
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

    private List<SearchCriteria> getParamsFromProfileView (ProfileView profileView) {
        List<SearchCriteria> params = new ArrayList<>();

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

        return params;
    }

    private void saveEmployeeFromPageData (Long id, EmployeeDTO employeeDTO, Profile profile) {
        EmployeeDTO tempEmployeeDTO = employeeAndProfileService.findEmployeeByProfileId(id);
        Profile tempProfile = employeeAndProfileService.findProfileById(id);
        profile.setId(id);
        profile.setPassword(tempProfile.getPassword());
        employeeDTO.setIsActive(tempEmployeeDTO.getIsActive());
        employeeDTO.setId(tempEmployeeDTO.getId());
        employeeDTO.setProfileId(id);
        employeeAndProfileService.update(employeeDTO, profile);
    }

    private List<ProfileView> getProfileViewListFromParams(String id,
                                                           String name,
                                                           String surname,
                                                           String middleName,
                                                           String phone,
                                                           String email,
                                                           String banned) {
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
        return profileViewList;
    }
}
