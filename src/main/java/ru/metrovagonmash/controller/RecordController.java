package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.service.RecordServiceImpl;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordServiceImpl recordService;
}
