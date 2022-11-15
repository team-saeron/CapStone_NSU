package togethers.togethers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequiredArgsConstructor
public class memberController {
    @GetMapping("/member/mypage")
    public String mypage(){ return "member/mypage";}
    @GetMapping("/member/introduction")
    public String introduction(){ return "member/introduction";}
}
