package togethers.togethers.swaggercontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.dto.SignInRequestDto;
import togethers.togethers.dto.SignUpRequestDto;
import togethers.togethers.dto.UserDetailSaveDto;

@Controller
@RequiredArgsConstructor
public class ViewSwaggerController {

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

    @GetMapping("/introduction")
    public String userDetail(Model model){
        model.addAttribute("userDetailForm", new UserDetailSaveDto());
        return "member/introduction";
    }
}
