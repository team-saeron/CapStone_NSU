package togethers.togethers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("date","2022");
        return "home";
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }

<<<<<<< HEAD
=======
    @GetMapping("/agree")
    public String agree(){
        return "member/agree";
    }

>>>>>>> aff2bb1da86941373135a1cdbb44706dc94ba6bc
    @GetMapping("/join")
    public String join(){
        return "member/join";
    }

}

