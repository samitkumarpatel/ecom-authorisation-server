package net.efullstack.ecomauthorisationserver.routers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.efullstack.ecomauthorisationserver.models.User;
import net.efullstack.ecomauthorisationserver.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebController {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }
    @GetMapping("/signup")
    String signup(Model model) {
        model.addAttribute("user", new User(null,null,null));
        //return "redirect:/home";
        return "signup";
    }

    @PostMapping("/signup")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("greeting", user);
        System.out.println(user);
        userRepository.save(new User(null, user.username(), passwordEncoder.encode(user.password())));
        return "redirect:/login";
    }

}
