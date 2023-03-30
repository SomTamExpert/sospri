package ch.bbw.pr.sospri.channel;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;

/**
 * ChannelController
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Controller
public class ChannelController {
    @Autowired
    MessageService messageservice;
    @Autowired
    MemberService memberservice;
    @Autowired
    ChannelService channelservice;

    @GetMapping("/get-channels")
    public String getRequestChannel(Model model) {
        System.out.println("getRequestChannel");
        model.addAttribute("messages", messageservice.getAll());
        Message message = new Message();
        message.setContent("Der zweite Pfeil trifft immer.");
        model.addAttribute("message", message);
        model.addAttribute("channels", channelservice.getAll());
        return "channel";
    }

    @GetMapping("/get-channel")
    public String getChannelByName(@RequestParam(name = "name", required = true) String name, Model model) {
        System.out.println("getChannelByName: " + name);
        Channel channel = channelservice.getByTopic(name);
        model.addAttribute("messages", messageservice.getAllByChannelId(channel.getId()));

        Message message = new Message();
        message.setContent("Der zweite Pfeil trifft immer.");
        model.addAttribute("message", message);
        model.addAttribute("channels", channelservice.getAll());
        return "channel";
    }

    @GetMapping("/edit-channel")
    public String editChannel(@RequestParam(name = "id", required = true) long id, Model model) {
        Channel channel = channelservice.getById(id);
        System.out.println("editChannel get: " + channel);
        model.addAttribute("channel", channel);
        return "editchannel";
    }

    @PostMapping("/edit-channel")
    public String editChannel(Model model, @ModelAttribute @Valid Channel channel, BindingResult bindingResult) {
        System.out.println("postRequestChannel(): channel: " + channel.toString());
        if (bindingResult.hasErrors()) {
            System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("channel", channel);
            return "edit-channel";
        }
        Channel value = channelservice.getById(channel.getId());
        value.setTopic(channel.getTopic());
        value.setDescription(channel.getDescription());
        System.out.println("editChannel post: update channel" + value);
        channelservice.update(channel.getId(), value);
        return "redirect:/get-channels";
    }

    @GetMapping("/add-channel")
    public String addChannel(Model model) {
        System.out.println("addChannel get");
        model.addAttribute("channel", new Channel());
        return "addchannel";
    }

    @PostMapping("/add-channel")
    public String addChannel(Model model, @ModelAttribute @Valid Channel channel, BindingResult bindingResult) {
        System.out.println("postRequestChannel(): channel: " + channel.toString());
        if (bindingResult.hasErrors()) {
            System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("channel", channel);
            return "add-channel";
        }
        channelservice.add(channel);
        return "redirect:/get-channels";
    }

    @GetMapping("/delete-channel")
    public String deleteChannel(@RequestParam(name = "id", required = true) long id, Model model) {
        System.out.println("deleteChannel: " + id);
        List<Message> messagesToDelete = messageservice.findByChannelId(id);
        messageservice.deleteAll(messagesToDelete);
        return "redirect:/get-channels";
    }

}
