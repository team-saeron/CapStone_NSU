package togethers.togethers.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
@Controller
@RequiredArgsConstructor
public class LoginController {


    @GetMapping("/member/login")
    public String login(){
        return "member/login";
    }

}
