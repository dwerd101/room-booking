package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.service.mail.MailSenderService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestMailController {

    private final MailSenderService mailSenderService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody RecordTable recordTable) {
        mailSenderService.send(recordTable.getEmail(), "Hello Friend", "It's first my message which i " +
                "sent to you from spring-boot");
    }
}
