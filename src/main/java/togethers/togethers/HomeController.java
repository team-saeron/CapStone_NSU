package togethers.togethers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("data","2022");
        return "html/footer";
    }

    @GetMapping("/home")
    public String home() {
        return "html/header";
    }
}
