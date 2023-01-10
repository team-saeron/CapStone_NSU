package togethers.togethers.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import togethers.togethers.item.Member;


import javax.servlet.http.Cookie;
@Controller
@RequiredArgsConstructor
public class LoginController {


    @GetMapping("/member/login")
    public String login(){
        return "member/login";
    }

}
