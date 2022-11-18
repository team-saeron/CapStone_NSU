package togethers.togethers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/member/mypage")
    public String mypage(){ return "member/mypage";}
    @GetMapping("/member/introduction")
    public String introduction(){ return "member/introduction";}
}
