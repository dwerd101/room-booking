package ru.metrovagonmash.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.mapper.EmployeeMyMapper;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.*;
import ru.metrovagonmash.service.*;
import ru.metrovagonmash.service.mail.MailSenderService;

import java.util.concurrent.Callable;

@RestController
@AllArgsConstructor
public class GetUserController {
    private final EmployeeService employeeService;

    @GetMapping("/get-user")
    public Callable<ResponseEntity<EmployeeDTO>> getEmployee() {
        EmployeeDTO employeeDTO = employeeService.findByLogin(getUserAuth().getUsername());
        return () -> ResponseEntity.ok(employeeDTO);
    }

    private User getUserAuth () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
