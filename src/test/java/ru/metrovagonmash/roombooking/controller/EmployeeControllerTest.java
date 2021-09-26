package ru.metrovagonmash.roombooking.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.EmployeeService;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(classes={RoomBookingApplication.class})

public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private List<EmployeeDTO> employeeDTOList;

    @BeforeEach
    void init() {
        employeeDTOList = new ArrayList<>();
        employeeDTOList.add(EmployeeDTO.builder()
        .name("qqq")
        .surname("aaa")
        .middleName("zzz")
        .phone("12344321")
        .email("aaa@aaa.ru")
        .isActive(true)
        .build());
    }
    @Test
    void findAll_thenReturnOk() throws Exception {
        final String url = "/employee/";
        when(employeeService.findAll()).thenReturn(employeeDTOList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<EmployeeDTO>> responseEntity = (ResponseEntity<List<EmployeeDTO>>) mvcResult.getAsyncResult();

        assertEquals(employeeDTOList, responseEntity.getBody());
    }

    @Test
    void save_thenReturnCorrect() throws Exception {
        final String url = "/employee/save";
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("qwe")
                .surname("asd")
                .middleName("zxc")
                .email("asd@asd.ru")
                .phone("1234444321")
                .isActive(true)
                .build();
        when(employeeService.save(any())).thenReturn(employeeDTO);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapToJson(employeeDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<EmployeeDTO> responseEntity = (ResponseEntity<EmployeeDTO>) mvcResult.getAsyncResult();
        assertEquals(employeeDTO, responseEntity.getBody());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
