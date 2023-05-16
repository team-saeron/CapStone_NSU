package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.dto.login.SignInRequestDto;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpRequestDto;
import togethers.togethers.dto.login.SignUpResultDto;
import togethers.togethers.service.SignService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignController {

    Logger logger = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Value("${spring.oauth.kakao.client-id}")
    private String kakao_client_id;

    @Value("${spring.oauth.naver.client-id}")
    private String naver_client_id;





    /** 회원가입 GET,POST 매핑 관련 Controller**/
    @GetMapping(value = "/member/new")
    public String signUp(Model model)
    {
        logger.info("[signUp] GET 회원가입 컨트롤러 동작 ");
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        model.addAttribute("dto",signUpRequestDto);
        model.addAttribute("kakao_client_id",kakao_client_id);
        model.addAttribute("naver_client_id",naver_client_id);
        return "join";
    }


    @PostMapping(value = "/member/new")
    public String signUp(@Valid SignUpRequestDto dto, RedirectAttributes attr)
    {
        logger.info("[signUp] POST 회원가입 컨트롤러 동작");
        if(signService.idCheck(dto.getId())==false)
        {
            logger.info("[signUp] 아이디 중복 에러");
            attr.addFlashAttribute("DuplicatedIdError","이미 존재하는 아이디 입니다.");
            return "redirect:/member/new";
        }else {
            String email = dto.getEmail() + "@" + dto.getDomain();
            logger.info("[signUp] name:{}, nickname:{}, email:{} birth : {}", dto.getName(), dto.getNickname(), dto.getEmail(), dto.getBirth());
            dto.setRole("user");
            SignUpResultDto signUpResultDto = signService.signUp(dto);
            attr.addFlashAttribute("SuceesJoin", "회원가입이 완료 되었습니다. 로그인하여주세요");
            return "redirect:/login";
        }
    }





    /** 로그인 관련 GET, POST 매핑 관련 Controller**/
    @GetMapping(value = "/login")
    public String singIn(Model model)
    {
        logger.info("[singIn] GET 로그인 컨트롤러 동작");
        SignInRequestDto signInRequestDto = new SignInRequestDto();
        model.addAttribute("SignInRequestDto",signInRequestDto);
        model.addAttribute("kakao_client_id",kakao_client_id);
        model.addAttribute("naver_client_id",naver_client_id);
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String signIn(@Valid @ModelAttribute SignInRequestDto signInRequestDto,
                         HttpServletResponse response, RedirectAttributes attr)
    {
        logger.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", signInRequestDto.getId());
        SignInResultDto signInResultDto = signService.signIn(signInRequestDto);

        if(signInResultDto.getCode()==-1){
            attr.addFlashAttribute("id_error_msg","존재하지 않는 아이디 입니다");
            return "redirect:/login";

        } else if (signInResultDto.getCode()==-2) {
            attr.addFlashAttribute("password_error_msg","비밀번호가 일치하지 않습니다.");
            return "redirect:/login";
        }
        else{
            String token = signInResultDto.getToken();
            response.setHeader("X-AUTH-TOKEN",token);

            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return "redirect:/";
        }
    }
}
