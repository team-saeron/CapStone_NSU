package togethers.togethers.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import togethers.togethers.item.Member;

import javax.servlet.http.Cookie;

public class LoginController {
    @RequestMapping(value="/#",method= RequestMethod.GET)
    public ModelAndView loginForm(Member member, @CookieValue(value="REMEMVER", required=false) Cookie rememberCookie) throws Exception{
        if(rememberCookie!=null){
            member.setEmail(rememberCookie.getValue());
            member.setRememberEmail(true);
        }
        ModelAndView mv = new ModelAndView("/auth/loginProc");
        return mv;

    }

}
