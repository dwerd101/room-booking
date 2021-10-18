package ru.metrovagonmash.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.mapper.EmployeeMyMapper;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.repository.*;
import ru.metrovagonmash.service.*;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@AllArgsConstructor
public class AdminRoomController {

    private final ProfileViewService profileViewService;
    private final VscRoomService vscRoomService;
    private final MailSenderService mailSenderService;
    private final RecordTableService recordTableService;
    private final HistoryRecordTableEmployeeAndRecordTableService historyRecordTableEmployeeAndRecordTableService;


    @GetMapping("/admin")
    public String admin(ModelMap modelMap) {
        List<ProfileView> list = profileViewService.findAll();
        modelMap.addAttribute("employeeList", list);
        return "adminpage";
    }

    @GetMapping("/admin/calendar/{idRoom}")
    public String adminCalendar(@PathVariable String idRoom, ModelMap modelMap) {
        vscRoomService.findByNumberRoomIfNotFoundByNumberRoomThrowException(Long.parseLong(idRoom));
        List<VscRoom> vscRoomList = vscRoomService.findAll();
        modelMap.addAttribute("vscroomlist", vscRoomList);
        return "admincalendar";
    }


    @PostMapping("/admin/calendar/update/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO,
                                                                 @PathVariable String id) {
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(Long.parseLong(id));
        tempRecordTableDTO.setRoomId(vscRoomService.findById(tempRecordTableDTO.getNumberRoomId()).getNumberRoom().toString());
        String message = "Изменение в бронировании комнаты №" + tempRecordTableDTO.getRoomId() + "\n"
                + "Тема: " + tempRecordTableDTO.getTitle() + "\n"
                + "Старое время: " + "\n"
                + "Дата бронирования: " + tempRecordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + tempRecordTableDTO.getStart().toLocalTime()
                + " по " + tempRecordTableDTO.getEnd().toLocalTime() + "\n";
        tempRecordTableDTO.setStart(recordTableDTO.getStart());
        tempRecordTableDTO.setEnd(recordTableDTO.getEnd());
        RecordTableDTO resultRecordTableDTO = historyRecordTableEmployeeAndRecordTableService.update(tempRecordTableDTO,
                Long.parseLong(id));
        String subject = "Изменение в бронирование комнаты №" + tempRecordTableDTO.getRoomId();
        message = message
                + "Новое время: " + "\n"
                + "Дата бронирования: " + tempRecordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + tempRecordTableDTO.getStart().withZoneSameInstant(ZonedDateTime.now().getZone()).toLocalTime()
                + " по " + tempRecordTableDTO.getEnd().withZoneSameInstant(ZonedDateTime.now().getZone()).toLocalTime() + "\n"
                + "Подробнее: " + "http://localhost:8080/calendar/" + recordTableDTO.getRoomId();
        mailSenderService.send(tempRecordTableDTO.getEmail(), subject, message);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }


    @DeleteMapping("/admin/calendar/delete/")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@RequestBody RecordTableDTO recordTableDTO) {
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(recordTableDTO.getId());
        tempRecordTableDTO.setRoomId(vscRoomService.findById(tempRecordTableDTO.getNumberRoomId()).getNumberRoom().toString());
        RecordTableDTO resultRecordTableDTO = recordTableService.delete(recordTableDTO);
        String subject = "Отмена бронирования комнаты №" + tempRecordTableDTO.getRoomId();
        String message = "Отменено бронирование комнаты №" + tempRecordTableDTO.getRoomId() + "\n"
                + "Тема: " + tempRecordTableDTO.getTitle() + "\n"
                + "Дата бронирования: " + tempRecordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + tempRecordTableDTO.getStart().toLocalTime()
                + " по " + tempRecordTableDTO.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + "http://localhost:8080/calendar/" + tempRecordTableDTO.getRoomId();
        mailSenderService.send(tempRecordTableDTO.getEmail(), subject, message);
        return () -> ResponseEntity.ok(resultRecordTableDTO);

    }

    @PostMapping("/admin/save-user")
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

        return "redirect:/admin/find-by-param";
    }

}
