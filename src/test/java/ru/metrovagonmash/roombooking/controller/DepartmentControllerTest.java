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
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = {RoomBookingApplication.class})
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private List<Department> departmentList;

    @BeforeEach
    void init() {
        departmentList = new ArrayList<>();
        departmentList.add(Department.builder()
        .nameDepartment("Management")
        .position("manager")
        .build());
    }

    @Test
    void findAll_thenReturnOk() throws Exception {
        final String url = "/department/";
        when(departmentService.findAll()).thenReturn(departmentList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<Department>> responseEntity = (ResponseEntity<List<Department>>) mvcResult.getAsyncResult();

        assertEquals(departmentList, responseEntity.getBody());
    }

    @Test
    void save_thenReturnCorrect() throws Exception {
        final String url = "/department/save";
        Department department = Department.builder()
                .nameDepartment("CleaningService")
                .position("cleaner")
                .build();
        when(departmentService.save(any())).thenReturn(department);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(department)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<Department> responseEntity = (ResponseEntity<Department>) mvcResult.getAsyncResult();
        assertEquals(department, responseEntity.getBody());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
