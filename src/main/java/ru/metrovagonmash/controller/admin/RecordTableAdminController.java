package ru.metrovagonmash.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.RecordTableViewSearchCriteriaRepository;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;
import ru.metrovagonmash.service.RecordTableService;
import ru.metrovagonmash.specification.SearchCriteria;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/records")
public class RecordTableAdminController {
    private final RecordTableService recordTableService;
    private final RecordTableAndEmployeeService recordTableAndEmployeeService;
    private final RecordTableViewSearchCriteriaRepository recordTableViewSearchCriteriaRepository;

    @GetMapping("/")
    public String records(@RequestParam(value = "search", required = false) String search,
                          ModelMap modelMap) {
        List<RecordTableView> recordTableViewList;
        if (search != null) {
            List<SearchCriteria> params = new ArrayList<>();
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }

            recordTableViewList = recordTableViewSearchCriteriaRepository.search(params);
        }
        else {
            recordTableViewList = recordTableAndEmployeeService.findAll();
        }

        modelMap.addAttribute("recordTableViewList", recordTableViewList);
        modelMap.addAttribute("findRecord",new RecordTableView());
        return "recordadminpage";
    }

    @PostMapping("/")
    public String findRecords(@ModelAttribute("findRecord") RecordTableView findRecord,
                                  ModelMap modelMap) {
        List<SearchCriteria> params = new ArrayList<>();

        if (findRecord.getId() != null)
            params.add(new SearchCriteria("id",":",findRecord.getId()));
        if (!findRecord.getEmail().isEmpty())
            params.add(new SearchCriteria("email",":",findRecord.getEmail()));
        if (findRecord.getEmployeeId()!= null)
            params.add(new SearchCriteria("employeeId",":",findRecord.getEmployeeId()));
        if (!findRecord.getEmployeeName().isEmpty())
            params.add(new SearchCriteria("employeeName",":",findRecord.getEmployeeName()));
        if (!findRecord.getEmployeeSurname().isEmpty())
            params.add(new SearchCriteria("employeeSurname",":",findRecord.getEmployeeSurname()));
        if (!findRecord.getEmployeeMiddleName().isEmpty())
            params.add(new SearchCriteria("employeeMiddleName",":",findRecord.getEmployeeMiddleName()));
        if (findRecord.getVcsRoomNumberRoom()!= null)
            params.add(new SearchCriteria("vcsRoomNumberRoom",":",findRecord.getVcsRoomNumberRoom()));
        if (findRecord.getIsActive() != null)
            params.add(new SearchCriteria("isActive",":",findRecord.getIsActive()));
        if (!findRecord.getTitle().isEmpty())
            params.add(new SearchCriteria("title",":",findRecord.getTitle()));
        if (findRecord.getStartEvent() != null)
            params.add(new SearchCriteria("startEvent",":",findRecord.getStartEvent()));
        if (findRecord.getEndEvent() != null)
            params.add(new SearchCriteria("endEvent",":",findRecord.getEndEvent()));



        List<RecordTableView> list = recordTableViewSearchCriteriaRepository.search(params);

        modelMap.addAttribute("recordTableViewList", list);
        return "recordadminpage";
    }



    @PostMapping("/save")
    public String updateRecords(@RequestParam(name = "id") String id,
                                @RequestParam(name = "email") String email,
                                @RequestParam(name = "employeeId") String employeeId,
                                @RequestParam(name = "vcsRoomNumberRoom") String vcsRoomNumberRoom,
                                @RequestParam(name = "isActive") String isActive,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "startEvent") String startEvent,
                                @RequestParam(name = "endEvent") String endEvent
    ) {
        String[] idMas = id.split(",");
        String[] emailMas = email.split(",");
        String[] employeeIdMas = employeeId.split(",");
        String[] vcsRoomNumberRoomMas = vcsRoomNumberRoom.split(",");
        String[] isActiveMas = isActive.split(",");
        String[] titleMas = title.split(",");
        String[] startEventMas = startEvent.split(",");
        String[] endEventMas = endEvent.split(",");

        List<RecordTable> recordTableList = new ArrayList<>();
        for (int i=0; i<idMas.length; i++) {
            recordTableList.add(
                    RecordTable.builder()
                            .id(Long.parseLong(idMas[i]))
                            .email(emailMas[i])
                            .employeeId(Employee.builder().id(Long.parseLong(employeeIdMas[i])).build())
                            .numberRoomId(VscRoom.builder().id(Long.parseLong(vcsRoomNumberRoomMas[i])).build())
                            .isActive(Boolean.valueOf(isActiveMas[i]))
                            .title(titleMas[i])
                            .startEvent(ZonedDateTime.parse(startEventMas[i]))
                            .endEvent(ZonedDateTime.parse(endEventMas[i]))
                            .build()
            );
        }
        recordTableService.batchUpdateRecords(recordTableList);

        return "redirect:/admin/records/";
    }

    @GetMapping("/delete/{id}")
    public String askDeleteRecord(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("recordTableId", id);
        return "deleterecord";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecord(@PathVariable String id) {
        recordTableService.deleteById(Long.parseLong(id));
        return "redirect:/admin/records/";
    }

}
