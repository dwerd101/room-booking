package ru.metrovagonmash.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.mapper.EmployeeMyMapper;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.repository.*;
import ru.metrovagonmash.service.*;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MainPageController {
    private final VscRoomService vscRoomService;

    @GetMapping("/")
    public String indexPage(ModelMap modelMap) {
        List<VscRoom> vscRoomList = vscRoomService.findAll();
        modelMap.addAttribute("vscroomlist", vscRoomList);
        return "index";
    }

}
