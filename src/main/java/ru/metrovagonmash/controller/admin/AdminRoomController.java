package ru.metrovagonmash.controller.admin;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import ru.metrovagonmash.model.ProfileView;

import ru.metrovagonmash.service.impl.ProfileViewServiceImpl;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminRoomController {
    private final ProfileViewServiceImpl profileViewService;


    @GetMapping("/admin")
    public String admin(ModelMap modelMap) {
        List<ProfileView> list = profileViewService.findAll();
        modelMap.addAttribute("employeeList", list);
        return "adminpage";
    }

}
