package ch.bbw.pr.sospri.security;

import ch.bbw.pr.sospri.Recaptcha.ReCaptchaValidationService;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * RegisterController
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Slf4j
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @Autowired
    ReCaptchaValidationService reCaptchaValidationService;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        log.info("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegisterMembers(@Valid RegisterMember registerMember, BindingResult bindingResult, @RequestParam(name = "g-recaptcha-response") String captcha, Model model) {
        log.info("postRequestRegisterMembers");
        if (! reCaptchaValidationService.validateCaptcha(captcha)) {
            log.warn("captcha of registerMember {} is not valid", registerMember.getPrename() + " " + registerMember.getPrename());
            bindingResult.rejectValue("prename", "error.prename", "the captcha is not valid.");
        }
        if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
            log.warn("passwords of registerMember {} do not match", registerMember.getPrename() + " " + registerMember.getPrename());
            bindingResult.rejectValue("confirmation", "password.mismatch", "the passwords do not match");
        }
        if (memberservice.getUserByUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase()) != null) {
            log.error("username of registerMember {} is already taken", registerMember.getPrename() + " " + registerMember.getLastname());
            bindingResult.rejectValue("prename", "error.prename", "the username is already taken.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("bindingResult of registerMember {} has errors", registerMember.getPrename() + " " + registerMember.getLastname());
            return "register";
        } else {
            memberservice.add(registerMember);
            return "login";
        }
    }
}
