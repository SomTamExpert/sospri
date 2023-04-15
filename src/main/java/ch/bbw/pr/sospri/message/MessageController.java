package ch.bbw.pr.sospri.message;

import ch.bbw.pr.sospri.channel.ChannelService;
import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
public class MessageController {
    @Autowired
    MessageService messageservice;
    @Autowired
    MemberService memberservice;
    @Autowired
    ChannelService channelservice;


    @GetMapping("/edit-message")
    public String editMessage(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("getEditMessage with id: " + id);
        Message message = messageservice.getById(id);
        model.addAttribute("message", message);
        return "editmessage";
    }

    @PostMapping("/edit-message")
    public String editMessage(Message message) {
        log.info("postEditMessage with id: " + message.getId());
        Message value = messageservice.getById(message.getId());
        value.setContent(message.getContent());
        messageservice.update(message.getId(), value);
        log.info("postEditMessage with id: " + message.getId() + " done");
        return "redirect:/get-channels";
    }

    @PostMapping("/add-message")
    public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult) {
        log.info("postRequestChannel(): message: " + message.toString());
        if (bindingResult.hasErrors()) {
            log.warn("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("messages", messageservice.getAll());
            return "channel";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member tmpMember;
        if (authentication != null) {
            log.info("postRequestChannel(): authentication: " + authentication.getName());
            tmpMember = memberservice.getUserByUsername(authentication.getName());
        } else {
            log.warn("postRequestChannel(): authentication is null");
            return "redirect:/login";
        }
        message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
        message.setOrigin(new Date());
        messageservice.add(message);
        log.info("postRequestChannel(): message {} ", message + " added");
        return "redirect:/get-channels";
    }

    @GetMapping("/delete-message")
    public String deleteMessage(@RequestParam(name = "id", required = true) long id) {
        log.info("getDeleteMessage by id {} ", id);
        messageservice.deleteById(id);
        return "redirect:/get-channels";
    }
}
