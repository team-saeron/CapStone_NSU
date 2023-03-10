package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.dto.*;
import togethers.togethers.service.SignService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignController {

    Logger logger = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    /** 회원가입 GET,POST 매핑 관련 Controller**/

    @GetMapping(value = "/members/new")
    public String signUp(Model model)
    {
        logger.info("[signUp] GET 회원가입 컨트롤러 동작 ");
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        model.addAttribute("dto",signUpRequestDto);
        return "join";
    }


    @PostMapping(value = "/members/new")
    public String signUp(@Valid SignUpRequestDto dto, Model model)
    {
        logger.info("[signUp] POST 회원가입 컨트롤러 동작");
        String email = dto.getEmail()+"@"+dto.getDomain();
        logger.info("[signUp] name:{}, nickname:{}, email:{}",dto.getName(),dto.getNickname(), email);
        dto.setRole("user");
        SignUpResultDto signUpResultDto = signService.signUp(dto);

        SignInRequestDto signInRequestDto = new SignInRequestDto();
        model.addAttribute("SignInRequestDto",signInRequestDto);
        return "member/login";
    }


    /** 로그인 관련 GET, POST 매핑 관련 Controller**/

    @GetMapping(value = "/login")
    public String singIn(Model model)
    {
        logger.info("[singIn] GET 로그인 컨트롤러 동작");
        SignInRequestDto signInRequestDto = new SignInRequestDto();
        model.addAttribute("SignInRequestDto",signInRequestDto);
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String signUp(@Valid @ModelAttribute SignInRequestDto signInRequestDto,
                         HttpServletResponse response)
    {
        logger.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", signInRequestDto.getId());
        SignInResultDto signInResultDto = signService.signIn(signInRequestDto);


        String token = signInResultDto.getToken();
        response.setHeader("X-AUTH-TOKEN",token);

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        if(signInResultDto.getCode()==0){
            logger.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", signInRequestDto.getId(), signInResultDto.getToken());
        }
        return "redirect:/";
    }

}
