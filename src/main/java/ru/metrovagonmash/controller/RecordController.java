package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.exception.RecordTableException;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.RecordTableAndEmployeeService;
import ru.metrovagonmash.service.RecordTableService;
import ru.metrovagonmash.service.mail.MailSenderService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final RecordTableService recordService;
    private final RecordTableAndEmployeeService recordTableAndEmployeeService;
    private final MailSenderService mailSenderService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAll() {
        return () -> ResponseEntity.ok(recordService.findAll());
    }

    @GetMapping("/{index}")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findByIndex(@PathVariable String index) {
        return () -> ResponseEntity.ok(recordService.findByNumberRoomId(Long.parseLong(index)));

    }

    @PostMapping("/save/")
    public Callable<ResponseEntity<RecordTableDTO>> saveRecord(@RequestBody RecordTableDTO recordTableDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String[] urlMassive = recordTableDTO.getRoomId().split("/");
        recordTableDTO.setRoomId(urlMassive[urlMassive.length-1]);
        Callable<ResponseEntity<RecordTableDTO>> responseEntityCallableRecordTableDTO =
                () -> ResponseEntity.ok(recordTableAndEmployeeService.save(recordTableDTO, user));
        RecordTableDTO savedRecordTableDTO = null;
        try {
            savedRecordTableDTO = responseEntityCallableRecordTableDTO.call().getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String subject = "Бронирование комнаты №" + savedRecordTableDTO.getNumberRoomId();
        String message = "Вы забронировали комнату №" + recordTableDTO.getRoomId() + "\n"
                + "Тема: " + recordTableDTO.getTitle() + "\n"
                + "Дата бронирования: " + recordTableDTO.getStart().toLocalDate() + "\n"
                + "Время бронирования: с " + recordTableDTO.getStart().toLocalTime()
                + " по " + recordTableDTO.getEnd().toLocalTime() + "\n"
                + "Подробнее: " + "http://localhost:8080/calendar/" + savedRecordTableDTO.getNumberRoomId();
        mailSenderService.send(savedRecordTableDTO.getEmail(), subject,
                message);
        return responseEntityCallableRecordTableDTO;

    }

    @PostMapping("/update/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO, @PathVariable String id) {
        return () -> ResponseEntity.ok(recordService.update(recordTableDTO, Long.parseLong(id)));
    }



    @DeleteMapping("/delete/")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@RequestBody RecordTableDTO recordTableDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return () -> ResponseEntity.ok(recordTableAndEmployeeService.delete(recordTableDTO, user));
       // return () -> ResponseEntity.ok(recordService.deleteById(Long.parseLong(id)));
    }

    //Подумать над названием
    @GetMapping("/findAll")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom() {
        return () -> ResponseEntity.ok(recordService.findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom());
    }



}
