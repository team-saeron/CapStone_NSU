
package togethers.togethers.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import togethers.togethers.domain.Member;
import togethers.togethers.service.MemberService;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("membersLogin",new LoginForm());
        return "login";
    }


    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult bindingResult) {
      Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

      if(loginMember == null){
          bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
          return "login";
      }
      return "redirect:/";

    }

}