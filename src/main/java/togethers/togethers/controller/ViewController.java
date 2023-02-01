package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.data.dto.SignInRequestDto;
import togethers.togethers.data.dto.SignUpRequestDto;
import togethers.togethers.data.dto.UserDetailSaveDto;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/sign-api/sign-up")
    public String signUpView(Model model){
        model.addAttribute("signUpView", new SignUpRequestDto());
        return "join";
    }

    @GetMapping("/sign-api/sign-in")
    public String signInView(Model model){
        model.addAttribute("loginForm", new SignInRequestDto());
        return "member/login";
    }

    @GetMapping("/introduction/{id}")
    public String userDetail(Model model){
        model.addAttribute("userDetailForm", new UserDetailSaveDto());
        return "member/introduction";
    }
}
