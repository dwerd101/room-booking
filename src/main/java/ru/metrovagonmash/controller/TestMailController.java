package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.RecordTable;
import ru.metrovagonmash.service.mail.MailSenderService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TestMailController {

    private final MailSenderService mailSenderService;

    @PostMapping("/send")
    @ResponseBody
    public void sendMessage(@RequestBody RecordTable recordTable) {
        mailSenderService.send(recordTable.getEmail(), "Hello Friend", "It's first my message which i " +
                "sent to you from spring-boot");
    }

    @GetMapping("/index")
    public String index() {
        return "rooms";
    }

    @GetMapping("/enable")
    @ResponseBody
    public String active() {
        return "активен";
    }
}
