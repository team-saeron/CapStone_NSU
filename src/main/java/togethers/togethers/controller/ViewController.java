package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserRepository userRepository;

    @GetMapping("/sign-api/sign-up")
    String signUpView(Model model){
        //model.addAttribute("signUpView", new User());
        return "join";
    }

    @GetMapping("/sign-api/sign-in")
    String signInView(Model model){
        //model.addAttribute("loginForm", new User());
        return "member/login";
    }
}
