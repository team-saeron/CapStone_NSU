package togethers.togethers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TogetherController {

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("data","hello!!!!");
        return "html/footer";
    }

    @GetMapping("/home")
    public String home() {
        return "html/header";
    }

}