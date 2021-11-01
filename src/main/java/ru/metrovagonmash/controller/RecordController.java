package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.exception.RecordTableBadRequestException;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.HistoryRecordTableEmployeeAndRecordTableService;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;
import ru.metrovagonmash.service.RecordTableService;
import ru.metrovagonmash.service.VscRoomService;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@PropertySource("classpath:record-text.properties")
@RequestMapping("/record")
public class RecordController {
    @Value("${record.url}")
    private String recordUrl;
    private final RecordTableService recordTableService;
    private final RecordTableAndEmployeeService recordTableAndEmployeeService;
    private final HistoryRecordTableEmployeeAndRecordTableService<RecordTableDTO, User, Long> historyRecordTableEmployeeAndRecordTableService;
    private final MailSenderService mailSenderService;
    private final VscRoomService vscRoomService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAll() {
        return () -> ResponseEntity.ok(recordTableService.findAll());
    }

    @GetMapping("/{index}")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findByIndex(@PathVariable String index) {
        return () -> ResponseEntity.ok(recordTableService.findByNumberRoom(Long.parseLong(index)));

    }

    @PostMapping("/save/")
    public Callable<ResponseEntity<RecordTableDTO>> saveRecord(@RequestBody RecordTableDTO recordTableDTO) {
        setCorrectRoomIdFormat(recordTableDTO);
        RecordTableDTO resultRecordTableDto = historyRecordTableEmployeeAndRecordTableService.save(recordTableDTO, getUserAuth());
        sendConfirmMessageToEmployee(resultRecordTableDto, recordTableDTO.getRoomId());
        return () -> ResponseEntity.ok(recordTableDTO);
    }



    @PostMapping("/update/")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO) {
        checkPermissionToEditRecord(getUserAuth().getUsername(), recordTableDTO);
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


    @DeleteMapping("/delete/")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@RequestBody RecordTableDTO recordTableDTO) {
        checkPermissionToEditRecord(getUserAuth().getUsername(), recordTableDTO);
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(recordTableDTO.getId());
        RecordTableDTO resultRecordTableDTO = recordTableService.delete(recordTableDTO);
        sendConfirmDeleteMessageToEmployee(tempRecordTableDTO);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }


    @GetMapping("/findAll")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAllByEmployeeFullNameAndRecordAndIsActiveAndNumberRoom() {
        return () -> ResponseEntity.ok(recordTableService.findAllByEmployeeFullNameAndRecordAndIsActiveAndNumberRoom());
    }

    private User getUserAuth () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    // FIXME: 31.10.2021 Переделать получение времени в корректном часовом поясе
    private ZonedDateTime toCurrentZone(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(ZonedDateTime.now().getZone());
    }

    private void sendConfirmMessageToEmployee(RecordTableDTO recordTableDTO, String roomId) {
        recordTableDTO.setRoomId(roomId);
        String subject = "Бронирование комнаты №" + recordTableDTO.getRoomId();
        String message = getMessageForSaveRecord(recordTableDTO);
        mailSenderService.send(recordTableDTO.getEmail(), subject, message);
    }

    private String getMessageForSaveRecord(RecordTableDTO recordTableDTO) {
        return "Вы забронировали комнату №" + recordTableDTO.getRoomId() + "\n"
                + "Тема: " + recordTableDTO.getTitle() + "\n"
                + "Дата бронирования: " + recordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + toCurrentZone(recordTableDTO.getStart()).toLocalTime()
                + " по " + toCurrentZone(recordTableDTO.getEnd()).toLocalTime() + "\n"
                + "Подробнее: " + recordUrl + recordTableDTO.getRoomId();
    }

    private void setCorrectRoomIdFormat (RecordTableDTO recordTableDTO) {
        String[] urlMassive = recordTableDTO.getRoomId().split("/");
        recordTableDTO.setRoomId(urlMassive[urlMassive.length - 1]);
    }

    private void checkPermissionToEditRecord(String login, RecordTableDTO recordTableDTO) {
        if (!recordTableAndEmployeeService.checkPermissionByLoginAndRecordId(login,recordTableDTO.getId())) {
            throw new RecordTableBadRequestException("Нет доступа к записи!");
        }
    }

    private void sendConfirmUpdateMessageToEmployee(RecordTableDTO previousRecordTableDTO, RecordTableDTO recordTableDTO) {
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
                + "Время бронирования: с " + toCurrentZone(recordTableDTO.getStart()).toLocalTime()
                + " по " + toCurrentZone(recordTableDTO.getEnd()).toLocalTime() + "\n"
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


}
