package ru.metrovagonmash.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.config.search.SearchByURLParams;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.repository.search.DepartmentSearchCriteriaRepositoryImpl;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.specification.SearchCriteria;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/departments")
public class DepartmentsAdminController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final DepartmentSearchCriteriaRepositoryImpl departmentSearchCriteriaRepository;
    private final SearchByURLParams searchByURLParams;

    @GetMapping("/")
    public String departments(@RequestParam(value = "search", required = false) String search,
                              ModelMap modelMap) {
        List<Department> departmentList;
        if (search != null) {
            departmentList = departmentSearchCriteriaRepository.search(searchByURLParams.getParamsFromSearch(search));
        }
        else {
            departmentList = departmentService.findAll();
        }
        modelMap.addAttribute("departmentList", departmentList);
        modelMap.addAttribute("findDepartment",new Department());
        return "departmentadminpage";
    }

    @PostMapping("/")
    public String findDepartments(@ModelAttribute("findDepartment") Department findDepartment,
                                  ModelMap modelMap) {
        List<Department> list = departmentSearchCriteriaRepository.search(getParamsFromDepartment(findDepartment));

        modelMap.addAttribute("departmentList", list);
        return "departmentadminpage";
    }

    @PostMapping("/save")
    public String updateDepartments(@RequestParam(name = "id") String id,
                              @RequestParam(name = "departmentName") String departmentName,
                              @RequestParam(name = "position") String position) {
        departmentService.batchUpdateDepartment(getDepartmentListFromParams(id, departmentName, position));

        return "redirect:/admin/departments/";
    }

    @GetMapping("/delete/{id}")
    public String askDeleteDepartment(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("departmentId", id);
        modelMap.addAttribute("message", getMessageForDeleteDepartmentPage(Long.parseLong(id)));
        return "deletedepartment";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable String id) {
        departmentService.deleteById(Long.parseLong(id));
        return "redirect:/admin/departments/";
    }

    @GetMapping("/add")
    public String addDepartment(ModelMap modelMap) {
        modelMap.addAttribute("departmentData", new Department());
        return "addingdepartment";
    }

    @PostMapping("/add")
    public String saveNewDepartment(@ModelAttribute("departmentData")final @Valid Department department) {
        departmentService.save(department);
        return "redirect:/admin/departments/";
    }


    private List<SearchCriteria> getParamsFromDepartment(Department findDepartment) {
        List<SearchCriteria> params = new ArrayList<>();

        if (findDepartment.getId() != null)
            params.add(new SearchCriteria("id",":",findDepartment.getId()));
        if (findDepartment.getNameDepartment() != null)
            params.add(new SearchCriteria("nameDepartment",":",findDepartment.getNameDepartment()));
        if (findDepartment.getPosition() != null)
            params.add(new SearchCriteria("position",":",findDepartment.getPosition()));
        return params;
    }

    private String getMessageForDeleteDepartmentPage (Long id) {
        String departmentName = departmentService.findById(id).getNameDepartment();
        if (employeeService.isPresentByDepartmentId(id))
            return "В департаменте " + departmentName + " остались сотрудники, удалить его?";
        else return "Удалить департамент " + departmentName + "?";
    }

    private List<Department> getDepartmentListFromParams(String id,
                                                         String departmentName,
                                                         String position) {
        String[] idMas = id.split(",");
        String[] departmentNameMas = departmentName.split(",");
        String[] positionMas = position.split(",");

        List<Department> departmentList = new ArrayList<>();
        for (int i=0; i<idMas.length; i++) {
            departmentList.add(
                    Department.builder()
                            .id(Long.parseLong(idMas[i]))
                            .nameDepartment(departmentNameMas[i])
                            .position(positionMas[i])
                            .build()
            );
        }
        return departmentList;
    }
}
