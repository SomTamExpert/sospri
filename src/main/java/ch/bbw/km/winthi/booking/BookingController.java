package ch.bbw.km.winthi.booking;

import ch.bbw.km.winthi.member.Member;
import ch.bbw.km.winthi.member.MemberService;
import ch.bbw.km.winthi.room.Room;
import ch.bbw.km.winthi.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * BookingController
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Slf4j
@Controller
public class BookingController {
    @Autowired
    MemberService memberservice;
    @Autowired
    BookingService bookingService;
    @Autowired
    RoomService roomService;



    @GetMapping("/edit-booking")
    public String editBooking(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("getEditBooking with id: " + id);
        Booking booking = bookingService.getById(id);
        model.addAttribute("booking", booking);
        return "editbooking";
    }



    @PostMapping("/edit-booking")
    public String editBooking(Booking booking) {
        log.info("postEditBooking with id: " + booking.getId());
        Booking value = bookingService.getById(booking.getId());
        value.setStartDate(booking.getStartDate());
        value.setEndDate(booking.getEndDate());
        bookingService.update(booking.getId(), value);
        log.info("postEditBooking with id: " + booking.getId() + " done");
        return "redirect:/get-bookings";
    }

    @PostMapping("/add-booking")
    public String postRequestChannel(
            Model model,
            @ModelAttribute @Valid Booking booking,
            BindingResult bindingResult,
            HttpServletRequest request // Inject HttpServletRequest to access the current request
    ) {
        log.info("postRequestChannel(): booking: " + booking.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member tmpMember;

        // Check if the user is anonymous (not authenticated)
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            log.warn("postRequestChannel(): User is not authenticated. Redirecting to login.");
            return "redirect:/login";
        }

        // Continue with the rest of your code
        tmpMember = memberservice.getUserByUsername(authentication.getName());
        booking.setMemberId(tmpMember.getId());

        Booking createdBooking = bookingService.create(booking);

        if (createdBooking == null) {
            log.warn("Time slot is already booked");
            bindingResult.rejectValue("endDate", "timeSlotBooked", "Time slot is already booked");
            model.addAttribute("booking", booking);
            Room room = roomService.getById(booking.getRoomId());
            model.addAttribute("room", room);
            return "booking-form";
        }

        if (bindingResult.hasErrors()) {
            log.warn("postRequestBooking(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("booking", booking);
            Room room = roomService.getById(booking.getRoomId());
            model.addAttribute("room", room);
            return "booking-form";
        }

        log.info("postRequestChannel(): booking {} ", booking + " added");
        return "redirect:/get-rooms";
    }

    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam(name = "id", required = true) long id) {
        log.info("getDeleteBooking by id {} ", id);
        bookingService.deleteById(id);
        return "redirect:/get-bookings";
    }
}
