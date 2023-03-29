package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import javax.validation.Valid;

/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegisterMembers(@Valid RegisterMember registerMember, BindingResult bindingResult, Model model ) {
        if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
            bindingResult.rejectValue("confirmation", "password.mismatch", "the passwords do not match");
        }
        if (memberservice.getUserByUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase()) != null) {
            bindingResult.rejectValue("prename", "error.prename", "the username is already taken.");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            memberservice.add(registerMember);
            return "registerconfirmed";
        }
    }
}
