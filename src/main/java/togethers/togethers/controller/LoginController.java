package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import togethers.togethers.entity.Member;
import togethers.togethers.form.LoginForm;
import togethers.togethers.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {
    public final MemberService memberService;


    @GetMapping("/login")
    public String loginForm(Model model){
       model.addAttribute("membersLogin", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginForm form, HttpServletRequest request) throws Exception{
        Cookie[] cookies = request.getCookies();
        String id = cookies[((int)cookies.length-1)].getValue();

        Member loginMember  = memberService.login(form.getLoginId(), form.getLoginPw());

        if(loginMember == null){
            request.setAttribute("msg","아이디 또는 비밀번호가 틀렸습니다.");
            return "home";
        }

        return "redirect:/";
    }



}
