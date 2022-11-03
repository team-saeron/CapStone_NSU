package togethers.togethers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String main(Model model) {
        //String strYear = (String)request.getAttribute("strYear");
        model.addAttribute("date","2022");
        return "home";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
