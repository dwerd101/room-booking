package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.service.VscRoomServiceImpl;

@RestController
@RequiredArgsConstructor
public class VscRoomController {
    private final VscRoomServiceImpl vscRoomService;
}
