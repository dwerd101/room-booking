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
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.RecordTableService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
//@WebMvcTest(QuoteApiController.class)
@SpringBootTest(classes={RoomBookingApplication.class})
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordTableService recordTableService;

    private List<RecordTableDTO> recordTableDTOList;

    @BeforeEach
    void init() {
        recordTableDTOList = new ArrayList<>();
        recordTableDTOList.add(RecordTableDTO.builder()
        .employeeName("Roman")
        .employeeSurname("Test")
        .build());
        recordTableDTOList.add(RecordTableDTO.builder()
                .employeeName("Andrey")
                .employeeSurname("Test1")
                .build());
    }

    @Test
    void findAll_thenReturnOk() throws Exception {
        final String url = "/record/";
        when(recordTableService.findAll()).thenReturn(recordTableDTOList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<RecordTableDTO>> responseEntity = (ResponseEntity<List<RecordTableDTO>>) mvcResult.getAsyncResult();

        assertEquals(recordTableDTOList, responseEntity.getBody());
    }

    @Test
    void save_thenReturnCorrect() throws Exception {
        final String url = "/record/save";
      RecordTableDTO recordTableDTO =  RecordTableDTO.builder()
                .employeeName("Roman")
                .employeeSurname("Test")
                .build();
        when(recordTableService.save(any())).thenReturn(recordTableDTO);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(recordTableDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<RecordTableDTO> responseEntity = (ResponseEntity<RecordTableDTO>) mvcResult.getAsyncResult();
        assertEquals(recordTableDTO, responseEntity.getBody());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
