package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.RecordTableServiceImpl;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final RecordTableServiceImpl recordService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAll() {
        return () -> ResponseEntity.ok(recordService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<RecordTableDTO>> saveEmployee(@RequestBody RecordTableDTO recordTableDTO) {
        return () -> ResponseEntity.ok(recordService.save(recordTableDTO));
    }

    @PostMapping("/update/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> updateEmployee(@RequestBody RecordTableDTO recordTableDTO, @PathVariable String id) {
        return () -> ResponseEntity.ok(recordService.update(recordTableDTO, Long.parseLong(id)));
    }

    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> deleteEmployee( @PathVariable String id) {
        return () -> ResponseEntity.ok(recordService.deleteById(Long.parseLong(id)));
    }

    //Подумать над названием
    @GetMapping("/findAll")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom() {
        return () -> ResponseEntity.ok(recordService.findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom());
    }



}
