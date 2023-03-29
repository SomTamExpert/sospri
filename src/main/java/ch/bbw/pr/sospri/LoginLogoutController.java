package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.ResetMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginLogoutController {
    @Autowired
    MemberService memberservice;

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid ResetMember resetMember, BindingResult bindingResult, Model model) {
        if (!resetMember.getPassword().equals(resetMember.getConfirmation())) {
            bindingResult.rejectValue("confirmation", "password.mismatch", "the passwords do not match");
        }
        if (bindingResult.hasErrors()) {
            return "resetpassword";
        }
        String username = resetMember.getUsername();
        String password = resetMember.getPassword();
        memberservice.resetPassword(username, password);
        return "login";
    }
    @RequestMapping("/request-password-reset")
    public String requestPasswordReset(Model model) {
        model.addAttribute("resetMember", new ResetMember());
        return "requestpasswordreset";
    }
    @PostMapping("/request-password-reset")
    public String requestPasswordReset(@ModelAttribute("resetMember") ResetMember resetMember, BindingResult bindingResult, Model model) {
        String username = resetMember.getUsername();
        String challengeAnswer = resetMember.getChallenge();

        if (memberservice.isChallengeAnswerCorrect(username, challengeAnswer)) {
            model.addAttribute("resetMember", resetMember);
            return "resetpassword";
        } else {
            bindingResult.rejectValue("username", "username.or.challenge.mismatch", "Invalid username or challenge answer.");
            return "requestpasswordreset";
        }
    }
}
