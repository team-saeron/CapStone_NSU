package togethers.togethers.controller;

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


    @GetMapping("/agree")
    public String agree(){
        return "agree";
    }


    @GetMapping("/write")
    public String write(){
        return "write";
    }

    @GetMapping("/choose")
    public String choose(){
        return "choose";
    }






}
