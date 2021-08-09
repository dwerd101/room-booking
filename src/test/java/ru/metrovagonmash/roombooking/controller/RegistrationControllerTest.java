package ru.metrovagonmash.roombooking.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.metrovagonmash.config.security.Role;
import ru.metrovagonmash.model.dto.RegistrationDTO;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.DepartmentService;
import ru.metrovagonmash.service.RegistrationService;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {RoomBookingApplication.class})
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void saveEmployeeAndProfile_thenReturnOk() {
        final String url = "/registration";
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .surname("TestSurname")
                .name("TestName")
                .middleName("TestMiddleName")
                .phone("444")
                .email("test@test.com")
                .login("TestUser")
                .password("root")
                .isActive(true)
                .accountNonLocked(true)
                .role(Role.EMPLOYEE)
                .departmentId((long) 1)
                .build();

        when(registrationService.save(any())).thenReturn(registrationDTO);

        //MvcResult mvcResult = this.mockMvc.perform(MockM)

    }


}
