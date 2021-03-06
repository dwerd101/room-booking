package ru.metrovagonmash.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.VscRoom;
import ru.metrovagonmash.service.VscRoomService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/vscrooms")
public class VscRoomAdminController {
    private final VscRoomService vscRoomService;
    @GetMapping("/")
    public String vscRooms(ModelMap modelMap) {
        List<VscRoom> vscRoomList = vscRoomService.findAll();
        modelMap.addAttribute("vscRoomList", vscRoomList);
        return "vscroomadminpage";
    }

    @PostMapping("/save")
    public String updateVscRoom(@RequestParam(name = "id") String id,
                                    @RequestParam(name = "isActive") String isActive,
                                    @RequestParam(name = "numberRoom") String numberRoom
    ) {
        vscRoomService.batchUpdateVscRoom(getVscRoomListFromParams(id, isActive, numberRoom));

        return "redirect:/admin/vscrooms/";
    }

    @GetMapping("/delete/{id}")
    public String askDeleteRoom(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("roomId", id);
        return "deleteroom";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        vscRoomService.deleteById(Long.parseLong(id));
        return "redirect:/admin/vscrooms/";
    }

    @GetMapping("/addroom")
    public String addRoom(ModelMap modelMap) {
        modelMap.addAttribute("roomData", new VscRoom());
        return "addingroom";
    }

    @PostMapping("/addroom")
    public String saveNewRoom(@ModelAttribute("roomData")final @Valid VscRoom vscRoom) {
        vscRoomService.save(vscRoom);
        return "redirect:/admin/vscrooms/";
    }

    private List<VscRoom> getVscRoomListFromParams(String id,
                                                   String isActive,
                                                   String numberRoom) {
        String[] idMas = id.split(",");
        String[] isActiveMas = isActive.split(",");
        String[] numberRoomMas = numberRoom.split(",");

        List<VscRoom> vscRoomList = new ArrayList<>();
        for (int i=0; i<idMas.length; i++) {
            vscRoomList.add(
                    VscRoom.builder()
                            .id(Long.parseLong(idMas[i]))
                            .isActive(Boolean.valueOf(isActiveMas[i]))
                            .numberRoom(Long.parseLong(numberRoomMas[i]))
                            .build()
            );
        }
        return vscRoomList;
    }
}
