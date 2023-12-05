package ch.bbw.km.winthi.room;

import ch.bbw.km.winthi.booking.Booking;
import ch.bbw.km.winthi.category.CategoryService;
import ch.bbw.km.winthi.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class RoomController {
    @Autowired
    RoomService roomservice;
    @Autowired
    MemberService memberservice;
    @Autowired
    CategoryService categoryService;


    @GetMapping("/edit-room")
    public String editMessage(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("getEditMessage with id: " + id);
        Room room = roomservice.getById(id);
        model.addAttribute("room", room);
        return "editroom";
    }

    @PostMapping("/edit-room")
    public String editMessage(Room room) {
        log.info("postEditMessage with id: " + room.getId());
        Room value = roomservice.getById(room.getId());
        value.setContent(room.getContent());
        roomservice.update(room.getId(), value);
        log.info("postEditMessage with id: " + room.getId() + " done");
        return "redirect:/get-rooms";
    }

    @GetMapping("/get-rooms")
    public String getRooms(Model model) {
        log.info("getRooms");
        model.addAttribute("rooms", roomservice.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "rooms";
    }

    @GetMapping("get-booking-form")
    public String getBookingForm(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("getBookingForm for room with id: " + id);
        Room room = roomservice.getById(id);
        model.addAttribute("room", room);
        model.addAttribute("booking", new Booking());
        return "booking-form";
    }
}
