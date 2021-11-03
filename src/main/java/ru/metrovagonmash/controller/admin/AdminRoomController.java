package ru.metrovagonmash.controller.admin;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.exception.RecordTableBadRequestException;
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
@RequiredArgsConstructor
public class AdminRoomController {
    @Value("${record.url}")
    private String recordUrl;

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


    @PostMapping("/admin/calendar/update/")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO) {
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(recordTableDTO.getId());
        recordTableDTO.setEmail(tempRecordTableDTO.getEmail());
        recordTableDTO.setIsActive(tempRecordTableDTO.getIsActive());
        recordTableDTO.setNumberRoomId(tempRecordTableDTO.getNumberRoomId());
        recordTableDTO.setEmployeeId(tempRecordTableDTO.getEmployeeId());
        recordTableDTO.setRoomId(vscRoomService.findById(recordTableDTO.getNumberRoomId()).getNumberRoom().toString());
        RecordTableDTO resultRecordTableDTO = historyRecordTableEmployeeAndRecordTableService.update(recordTableDTO,
                recordTableDTO.getId());
        sendConfirmUpdateMessageToEmployee(tempRecordTableDTO, recordTableDTO);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }


    @DeleteMapping("/admin/calendar/delete/")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@RequestBody RecordTableDTO recordTableDTO) {
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(recordTableDTO.getId());
        RecordTableDTO resultRecordTableDTO = recordTableService.delete(recordTableDTO);
        sendConfirmDeleteMessageToEmployee(tempRecordTableDTO);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }

    private void sendConfirmUpdateMessageToEmployee(RecordTableDTO previousRecordTableDTO, RecordTableDTO recordTableDTO) {
        setCurrentZone(recordTableDTO);
        String subject = "Изменение в бронирование комнаты №" + recordTableDTO.getRoomId();
        String message = getMessageForUpdateRecord(previousRecordTableDTO, recordTableDTO);
        mailSenderService.send(recordTableDTO.getEmail(), subject, message);
    }

    private String getMessageForUpdateRecord(RecordTableDTO previousRecordTableDTO, RecordTableDTO recordTableDTO) {
        return "Изменение в бронировании комнаты №" + recordTableDTO.getRoomId() + "\n"
                + "Тема: " + recordTableDTO.getTitle() + "\n"
                + "Старое время: " + "\n"
                + "Дата бронирования: " + previousRecordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + previousRecordTableDTO.getStart().toLocalTime()
                + " по " + previousRecordTableDTO.getEnd().toLocalTime() + "\n"
                + "Новое время: " + "\n"
                + "Дата бронирования: " + recordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + recordTableDTO.getStart().toLocalTime()
                + " по " + recordTableDTO.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + recordUrl  + recordTableDTO.getRoomId();
    }

    private void sendConfirmDeleteMessageToEmployee(RecordTableDTO recordTableDTO) {
        recordTableDTO.setRoomId(vscRoomService.findById(recordTableDTO.getNumberRoomId()).getNumberRoom().toString());
        String subject = "Отмена бронирования комнаты №" + recordTableDTO.getRoomId();
        mailSenderService.send(recordTableDTO.getEmail(), subject, getMessageForDeleteRecord(recordTableDTO));
    }

    private String getMessageForDeleteRecord(RecordTableDTO recordTableDTO) {
        return "Отменено бронирование комнаты №" + recordTableDTO.getRoomId() + "\n"
                + "Тема: " + recordTableDTO.getTitle() + "\n"
                + "Дата бронирования: " + recordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + recordTableDTO.getStart().toLocalTime()
                + " по " + recordTableDTO.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + recordUrl  + recordTableDTO.getRoomId();
    }

    private void setCurrentZone(RecordTableDTO recordTableDTO) {
        recordTableDTO.setStart(recordTableDTO.getStart().withZoneSameInstant(recordTableDTO.getTimeZone()));
        recordTableDTO.setEnd(recordTableDTO.getEnd().withZoneSameInstant(recordTableDTO.getTimeZone()));
    }
}
