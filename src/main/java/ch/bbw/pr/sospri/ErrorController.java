package ch.bbw.pr.sospri;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/forbidden")
    public String getForbidden() {
        return "403.html";
    }

    @GetMapping("/notfound")
    public String getNotFound() {
        return "404.html";
    }
}
