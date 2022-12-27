package togethers.togethers.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import togethers.togethers.domain.Member;
import togethers.togethers.memberRepository.MemberRepository;
import togethers.togethers.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("membersLogin",new Member());
        return "member/login";
    }


    @PostMapping("/login")
    public Member login(LoginForm loginForm) {
        memberService.login(loginForm.getId(),loginForm.getPassword());


    }


}
