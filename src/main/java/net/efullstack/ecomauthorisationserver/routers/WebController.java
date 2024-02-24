package net.efullstack.ecomauthorisationserver.routers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }
    @GetMapping("/signup")
    String signup(Model model) {
        //signup logic here ...
        //return "redirect:/home";
        return "signup";
    }
}
