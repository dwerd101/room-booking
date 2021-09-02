package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.repository.DepartmentSearchCriteriaRepository;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.EmployeeService;
import ru.metrovagonmash.specification.SearchCriteria;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DepartmentsAdminController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final DepartmentSearchCriteriaRepository departmentSearchCriteriaRepository;

    @GetMapping("/departments")
    public String departments(ModelMap modelMap) {
        List<Department> departmentList = departmentService.findAll();
        modelMap.addAttribute("departmentList", departmentList);
        modelMap.addAttribute("findDepartment",new Department());
        return "departmentadminpage";
    }

    @PostMapping("/departments")
    public String findDepartments(//@RequestParam(name = "findById") String findById,
                                  //@RequestParam(name = "findByDepartmentName") String findByDepartmentName,
                                  //@RequestParam(name = "findByPosition") String findByPosition,
                                  @ModelAttribute("findDepartment") Department findDepartment,
                                  ModelMap modelMap) {
        List<SearchCriteria> params = new ArrayList<>();

        /*
        if (!findById.isEmpty())
            params.add(new SearchCriteria("id",":",Long.valueOf(findById)));
        if (!findByDepartmentName.isEmpty())
            params.add(new SearchCriteria("nameDepartment",":",findByDepartmentName));
        if (!findByPosition.isEmpty())
            params.add(new SearchCriteria("position",":",findByPosition));

         */

        if (findDepartment.getId() != null)
            params.add(new SearchCriteria("id",":",findDepartment.getId()));
        if (findDepartment.getNameDepartment() != null)
            params.add(new SearchCriteria("nameDepartment",":",findDepartment.getNameDepartment()));
        if (findDepartment.getPosition() != null)
            params.add(new SearchCriteria("position",":",findDepartment.getPosition()));

        List<Department> list = departmentSearchCriteriaRepository.search(params);
        /*
        modelMap.addAttribute("findById", findById);
        modelMap.addAttribute("findByDepartmentName", findByDepartmentName);
        modelMap.addAttribute("findByPosition", findByPosition);

         */
        modelMap.addAttribute("departmentList", list);
        return "departmentadminpage";
    }

    @PostMapping("/save-department")
    public String updateDepartments(@RequestParam(name = "id") String id,
                              @RequestParam(name = "departmentName") String departmentName,
                              @RequestParam(name = "position") String position
    ) {
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
        departmentService.batchUpdateDepartment(departmentList);

        return "redirect:/admin/departments";
    }

    @GetMapping("/department/delete/{id}")
    public String askDeleteDepartment(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("departmentId", id);
        String departmentName = departmentService.findById(Long.parseLong(id)).getNameDepartment();
        if (employeeService.isPresentByDepartmentId(Long.parseLong(id)))
            modelMap.addAttribute("message",
                    "В департаменте " + departmentName + " остались сотрудники, удалить его?");
        else modelMap.addAttribute("message", "Удалить департамент " + departmentName + "?");
        return "deletedepartment";
    }

    @PostMapping("/department/delete/{id}")
    public String deleteDepartment(@PathVariable String id) {
        departmentService.deleteById(Long.parseLong(id));
        return "redirect:/admin/departments";
    }

    @GetMapping("/adddepartment")
    public String addDepartment(ModelMap modelMap) {
        modelMap.addAttribute("departmentData", new Department());
        return "addingdepartment";
    }

    @PostMapping("/adddepartment")
    public String saveNewDepartment(@ModelAttribute("departmentData")final @Valid Department department) {
        departmentService.save(department);
        return "redirect:/admin/departments";
    }

}
