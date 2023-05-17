package togethers.togethers.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpResultDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SocialController {
    private Logger logger = LoggerFactory.getLogger(SocialController.class);
    private final SocialService socialService;

    @Autowired
    public SocialController(SocialService socialService) {
        this.socialService = socialService;
    }

    /************************************카카오 계정 회원 가입, 로그인************************************************/
    @GetMapping(value = "/kakao_callback")
    public String KakaoJoin(String code, RedirectAttributes attr)
    {
        logger.info("[KakaoJoin] Kakao 계정으로 회원가입 컨트롤러 동작.");

        KakaoProfile kakaoProfile = socialService.getJoinKakaoProfile(code);

        SignUpResultDto signUpResultDto = socialService.KakaoSignUp(kakaoProfile);
        if (signUpResultDto.getCode()==-1)
        {
            attr.addFlashAttribute("DuplicatedIdError","이미 존재하는 이메일 아이디 입니다.");
            return "redirect:/member/new";
        }
        else {
            attr.addFlashAttribute("SuceesJoin","회원가입이 완료 되었습니다. 로그인하여주세요");
            return "redirect:/login";
        }
    }

    @GetMapping("/kakao_login")
    public String KakaoLogin(String code, RedirectAttributes attr, HttpServletResponse res)
    {
        logger.info("[KakaoLogin] 카카오 로그인 컨트롤러 동작.");
        KakaoProfile kakaoProfile = socialService.getLoginKakaoProfile(code);

        SocialLoginDto socialLoginDto = SocialLoginDto.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .build();
        SignInResultDto signInResultDto = socialService.SocialLogin(socialLoginDto);

        if(signInResultDto.getCode()==-1)
        {
            attr.addFlashAttribute("id_error_msg","존재하지 않는 아이디 입니다");
            return "redirect:/login";
        }
        else
        {
            String token = signInResultDto.getToken();
            res.setHeader("X-AUTH-TOKEN",token);

            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);

            return "redirect:/";
        }
    }

    /************************************네이버 계정 회원 가입, 로그인************************************************/
    @GetMapping(value = "/naver_callback")
    public String NaverSocialSignUp(String code, String state, RedirectAttributes attr)
    {
        logger.info("[NaverSocialLogin] 네이버 계정으로 회원가입 컨트롤러 동작");
        NaverProfile naverProfile = socialService.getNaverProfile(code, state);

        SignUpResultDto signUpResultDto = socialService.NaverSignUp(naverProfile);
        if (signUpResultDto.getCode()==-1)
        {
            attr.addFlashAttribute("DuplicatedIdError","이미 존재하는 이메일 아이디 입니다.");
            return "redirect:/member/new";
        }
        else {
            attr.addFlashAttribute("SuceesJoin","회원가입이 완료 되었습니다. 로그인하여주세요");
            return "redirect:/login";
        }
    }

    @GetMapping("/naver_login")
    public String NaverLogin(String code,String state ,RedirectAttributes attr, HttpServletResponse res) {
        logger.info("[NaverLogin] 네이버 로그인 Controller 동작.");

        NaverProfile naverProfile = socialService.getNaverProfile(code, state);

        SocialLoginDto socialLoginDto = SocialLoginDto.builder()
                .email(naverProfile.getResponse().getEmail())
                .build();

        SignInResultDto signInResultDto = socialService.SocialLogin(socialLoginDto);


        if (signInResultDto.getCode() == -1) {
            attr.addFlashAttribute("id_error_msg", "존재하지 않는 아이디 입니다");
            return "redirect:/login";
        } else {
            String token = signInResultDto.getToken();
            res.setHeader("X-AUTH-TOKEN", token);

            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);

            return "redirect:/";
        }

    }

    /************************************구글 계정 회원 가입, 로그인************************************************/
    @GetMapping(value = "/google_callback")
    public String GoogleSocialSignUp(String code, RedirectAttributes attr)
    {
        logger.info("[GoogleSocialSignUp] 구글 계정으로 회원가입 컨트롤러 동작");
        GoogleProfile googleProfile = socialService.getGoogleProfile(code);

        SignUpResultDto signUpResultDto = socialService.GoogleSignUp(googleProfile);

        if (signUpResultDto.getCode()==-1)
        {
            attr.addFlashAttribute("DuplicatedIdError","이미 존재하는 이메일 아이디 입니다.");
            return "redirect:/member/new";
        }
        else {
            attr.addFlashAttribute("SuceesJoin","회원가입이 완료 되었습니다. 로그인하여주세요");
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/google_login")
    public String GoogleLogin(String code,RedirectAttributes attr, HttpServletResponse res)
    {
        GoogleProfile googleProfile = socialService.getGoogleLoginProfile(code);
        SocialLoginDto socialLoginDto = SocialLoginDto.builder()
                .email(googleProfile.getEmail())
                .build();
        SignInResultDto signInResultDto = socialService.SocialLogin(socialLoginDto);

        if (signInResultDto.getCode() == -1) {
            attr.addFlashAttribute("id_error_msg", "존재하지 않는 아이디 입니다");
            return "redirect:/login";
        } else {
            String token = signInResultDto.getToken();
            res.setHeader("X-AUTH-TOKEN", token);

            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);
            return "redirect:/";
        }
    }
}
