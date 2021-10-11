package ru.metrovagonmash.controller.user.room;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.service.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserRoomController {
    private final VscRoomService vscRoomService;

    @GetMapping("/room/{idRoom}")
    public String calendar(@PathVariable String idRoom, ModelMap modelMap) {
        vscRoomService.findByNumberRoomIfNotFoundByNumberRoomThrowException(Long.parseLong(idRoom));
        List<VscRoom> vscRoomList = vscRoomService.findAll();
        modelMap.addAttribute("vscroomlist", vscRoomList);
        return "calendar";
    }

}
