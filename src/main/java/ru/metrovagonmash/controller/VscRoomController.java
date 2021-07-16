package ru.metrovagonmash.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.service.VscRoomServiceImpl;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vsc_room")
public class VscRoomController {
    private final VscRoomServiceImpl vscRoomService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<VscRoom>>> findAll() {
        return () -> ResponseEntity.ok(vscRoomService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<VscRoom>> saveRoom(@RequestBody VscRoom vscRoom) {
        return () -> ResponseEntity.ok(vscRoomService.save(vscRoom));
    }

    @PutMapping("/update/{id}")
    public Callable<ResponseEntity<VscRoom>> updateRoom(@RequestBody VscRoom vscRoom, @PathVariable String id) {
        return () -> ResponseEntity.ok(vscRoomService.update(vscRoom,Long.parseLong(id)));
    }
    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<VscRoom>> deleteRoom(@PathVariable String id) {
        return () -> ResponseEntity.ok(vscRoomService.deleteById(Long.parseLong(id)));
    }
}
