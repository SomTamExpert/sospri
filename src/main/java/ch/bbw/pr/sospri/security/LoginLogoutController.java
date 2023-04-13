package ch.bbw.pr.sospri.security;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.ResetMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class LoginLogoutController {
    @Autowired
    MemberService memberservice;

    @RequestMapping("/login")
    public String login() {
        log.info("login");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        log.info("logout");
        return "logout";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid ResetMember resetMember, BindingResult bindingResult) {
        log.info("postResetPassword");

        if (!resetMember.getPassword().equals(resetMember.getConfirmation())) {
            log.warn("passwords of resetMember {} do not match", resetMember.getUsername());
            bindingResult.rejectValue("confirmation", "password.mismatch", "the passwords do not match");
        }
        if (bindingResult.hasErrors()) {
            log.warn("bindingResult of resetMember {} has errors", resetMember.getUsername());
            return "resetpassword";
        }
        String username = resetMember.getUsername();
        String password = resetMember.getPassword();
        memberservice.resetPassword(username, password);
        return "login";
    }
    @RequestMapping("/request-password-reset")
    public String requestPasswordReset(Model model) {
        log.info(" getRequestPasswordReset");
        model.addAttribute("resetMember", new ResetMember());
        return "requestpasswordreset";
    }
    @PostMapping("/request-password-reset")
    public String requestPasswordReset(@ModelAttribute("resetMember") ResetMember resetMember, BindingResult bindingResult, Model model) {
        log.info("postRequestPasswordReset");
        String username = resetMember.getUsername();
        String challengeAnswer = resetMember.getChallenge();
        if (memberservice.isChallengeAnswerCorrect(username, challengeAnswer)) {
            model.addAttribute("resetMember", resetMember);
            log.info("challenge answer correct from user {}", username);
            return "resetpassword";
        } else {
            bindingResult.rejectValue("username", "username.or.challenge.mismatch", "Invalid username or challenge answer.");
            log.warn("challenge answer incorrect from user {}", username);
            return "requestpasswordreset";
        }
    }
}
