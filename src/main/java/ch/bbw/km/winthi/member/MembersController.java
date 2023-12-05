package ch.bbw.km.winthi.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UsersController
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Slf4j
@Controller
public class MembersController {
   @Autowired
   MemberService memberservice;

   @GetMapping("/get-members")
   public String getRequestMembers(Model model) {
      log.info("getRequestMembers");
      model.addAttribute("members", memberservice.getAll());
      return "members";
   }

   @GetMapping("/edit-member")
   public String editMember(@RequestParam(name = "id", required = true) long id, Model model) {
      Member member = memberservice.getById(id);
      log.info("getEditMember {} ", member.getPrename() + " " + member.getLastname());
      model.addAttribute("member", member);
      return "editmember";
   }

   @PostMapping("/edit-member")
   public String editMember(Member member) {
      log.info("postEditMember {} ", member.getPrename() + " " + member.getLastname());
      Member value = memberservice.getById(member.getId());
      value.setAuthority(member.getAuthority());
      log.warn("postEditMember update Member {} ", value.getPrename() + " " + value.getLastname());
      memberservice.update(member.getId(), value);
      return "redirect:/get-members";
   }

   @GetMapping("/delete-member")
   public String deleteMember(@RequestParam(name = "id", required = true) long id) {
      log.warn("deleteMember: " + id);
      memberservice.deleteById(id);
      return "redirect:/get-members";
   }
}
