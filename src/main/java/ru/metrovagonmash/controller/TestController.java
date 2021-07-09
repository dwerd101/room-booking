package ru.metrovagonmash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/userpage")
    public String userPage(){
        return "userpage";
    }

    @GetMapping("/calendar")
    public String calendar(){
        return "calendar";
    }
}
