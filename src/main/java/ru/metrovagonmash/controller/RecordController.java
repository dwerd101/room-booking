package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String[] urlMassive = recordTableDTO.getRoomId().split("/");
        recordTableDTO.setRoomId(urlMassive[urlMassive.length - 1]);
        RecordTableDTO resultRecordTableDto = historyRecordTableEmployeeAndRecordTableService.save(recordTableDTO, user);
        String subject = "Бронирование комнаты №" + recordTableDTO.getRoomId();
        String message = "Вы забронировали комнату №" + recordTableDTO.getRoomId() + "\n"
                + "Тема: " + resultRecordTableDto.getTitle() + "\n"
                + "Дата бронирования: " + resultRecordTableDto.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + resultRecordTableDto.getStart().toLocalTime()
                + " по " + resultRecordTableDto.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + recordUrl + recordTableDTO.getRoomId();
        mailSenderService.send(resultRecordTableDto.getEmail(), subject, message);
        return () -> ResponseEntity.ok(recordTableDTO);

    }

    @PostMapping("/update/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO,
                                                                 @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!recordTableAndEmployeeService.checkPermissionByLoginAndRecordId(user.getUsername(),Long.parseLong(id))) {
            throw new RecordTableBadRequestException("Нет доступа к записи!");
        }

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
                + "Подробнее: " + recordUrl  + recordTableDTO.getRoomId();
        mailSenderService.send(tempRecordTableDTO.getEmail(), subject, message);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }


    @DeleteMapping("/delete/")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@RequestBody RecordTableDTO recordTableDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        RecordTableDTO tempRecordTableDTO = recordTableService.findById(recordTableDTO.getId());
        tempRecordTableDTO.setRoomId(vscRoomService.findById(tempRecordTableDTO.getNumberRoomId()).getNumberRoom().toString());
        RecordTableDTO resultRecordTableDTO = recordTableAndEmployeeService.delete(recordTableDTO, user);
        String subject = "Отмена бронирования комнаты №" + tempRecordTableDTO.getRoomId();
        String message = "Отменено бронирование комнаты №" + tempRecordTableDTO.getRoomId() + "\n"
                + "Тема: " + tempRecordTableDTO.getTitle() + "\n"
                + "Дата бронирования: " + tempRecordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + tempRecordTableDTO.getStart().toLocalTime()
                + " по " + tempRecordTableDTO.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + recordUrl  + tempRecordTableDTO.getRoomId();
        mailSenderService.send(tempRecordTableDTO.getEmail(), subject, message);
        return () -> ResponseEntity.ok(resultRecordTableDTO);
    }

    //Подумать над названием
    @GetMapping("/findAll")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom() {
        return () -> ResponseEntity.ok(recordTableService.findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom());
    }



}
