package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.dto.RecordTableDTO;
import ru.metrovagonmash.service.RecordTableService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final RecordTableService recordService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAll() {
        return () -> ResponseEntity.ok(recordService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<RecordTableDTO>> saveRecord(@RequestBody RecordTableDTO recordTableDTO) {
        return () -> ResponseEntity.ok(recordService.save(recordTableDTO));
    }

    @PostMapping("/update/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> updateRecord(@RequestBody RecordTableDTO recordTableDTO, @PathVariable String id) {
        return () -> ResponseEntity.ok(recordService.update(recordTableDTO, Long.parseLong(id)));
    }

    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<RecordTableDTO>> deleteRecord(@PathVariable String id) {
        return () -> ResponseEntity.ok(recordService.deleteById(Long.parseLong(id)));
    }

    //Подумать над названием
    @GetMapping("/findAll")
    public Callable<ResponseEntity<List<RecordTableDTO>>> findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom() {
        return () -> ResponseEntity.ok(recordService.findAllByEmployeeNameAndSurnameAndMiddleNameAndRecordAndIsActiveAndNumberRoom());
    }



}
