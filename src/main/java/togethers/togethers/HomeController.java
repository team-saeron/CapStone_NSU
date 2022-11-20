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

    @GetMapping("/agree")
    public String agree(){
        return "agree";
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @GetMapping("/write")
    public String write(){
        return "write";
    }




}

