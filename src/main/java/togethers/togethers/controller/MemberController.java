package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/member/mypage")
    public String mypage(Model model) {
        return "member/mypage";
    }


    @GetMapping("/member/introduction")
    public String introduction(Model model) {
        return "member/introduction";
    }

    @GetMapping("/member/rewriteIntroduction")
    public String rewriteIntroduction(Model model) {
        return "member/rewriteIntroduction";
    }
    @GetMapping("/member/showOFFIntroduction")
    public String showOFFIntroduction(Model model) {
        return "member/showOFFIntroduction";
    }
}