package togethers.togethers.Find;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FindController {
    @GetMapping("/find/mail")
    public String mail() {return "find/mail";}

    @GetMapping("/find/phone")
    public String phone() {return "find/phone";}

    @GetMapping("/find/choose")
    public String choose() {return "find/choose";}


    @GetMapping("/find/password")
    public String password() {return "find/password";}
}