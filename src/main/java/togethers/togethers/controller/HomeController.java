package togethers.togethers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.dto.PostSearchDto;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String Home(Model model)
    {
        PostSearchDto postSearchDto = new PostSearchDto();

        model.addAttribute("dto",postSearchDto);
        return "home";
    }
}
