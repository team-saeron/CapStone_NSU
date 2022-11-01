package togethers.togethers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class TogetherController {
    @RequestMapping("/")
    public String header(Model model){
        log.info("home controller");
        return "css/header";
    }
}