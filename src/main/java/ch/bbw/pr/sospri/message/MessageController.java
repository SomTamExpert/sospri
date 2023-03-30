package ch.bbw.pr.sospri.message;

import ch.bbw.pr.sospri.channel.ChannelService;
import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
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
        Message message = messageservice.getById(id);
        System.out.println("editMessage get: " + message);
        model.addAttribute("message", message);
        return "editmessage";
    }

    @PostMapping("/edit-message")
    public String editMessage(Message message, Model model) {
        System.out.println("editMessage post: edit message" + message);
        Message value = messageservice.getById(message.getId());
        value.setContent(message.getContent());
        System.out.println("editMessage post: update message" + value);
        messageservice.update(message.getId(), value);
        return "redirect:/get-channels";
    }

    @PostMapping("/add-message")
    public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult) {
        System.out.println("postRequestChannel(): message: " + message.toString());
        if (bindingResult.hasErrors()) {
            System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("messages", messageservice.getAll());
            return "channel";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member tmpMember;
        if (authentication != null) {
            tmpMember = memberservice.getUserByUsername(authentication.getName());
        } else {
            tmpMember = memberservice.getById(4L);
        }
        message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
        message.setOrigin(new Date());
        messageservice.add(message);

        return "redirect:/get-channels";
    }

    @GetMapping("/delete-message")
    public String deleteMessage(@RequestParam(name = "id", required = true) long id, Model model) {
        System.out.println("deleteMessage: " + id);
        messageservice.deleteById(id);
        return "redirect:/get-channels";
    }
}
