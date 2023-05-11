package togethers.togethers.social;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpResultDto;
import togethers.togethers.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class KakaoController {
    private Logger logger = LoggerFactory.getLogger(KakaoController.class);

    private final UserService userService;
    private final SocialService socialService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public KakaoController(SocialService socialService, PasswordEncoder passwordEncoder, UserService userService) {
        this.socialService = socialService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping(value = "/kakao_callback")
    public String KakaoJoin(String code, RedirectAttributes attr)
    {
        logger.info("[KakaoJoin] Kakao 계정으로 회원가입 컨트롤러 동작.");

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "dc6c22266260fc5caeb2c46de3dd83e8");
        params.add("redirect_uri", "http://localhost:8081/kakao_callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);


        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("[kakaoCallback] code : {}, KakaoToken", code, oAuthToken);


        /**카카오 서버로 부터받은 Token을 이용해 사용자 정보 요청하기**/
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
    public String KakaoLogin(String code, RedirectAttributes attr, HttpServletResponse httpResponse)
    {
        logger.info("[KakaoLogin] 카카오 로그인 Controller 동작.");
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "dc6c22266260fc5caeb2c46de3dd83e8");
        params.add("redirect_uri", "http://localhost:8081/kakao_login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("[kakaoCallback] code : {}, KakaoToken", code, oAuthToken);


        /**카카오 서버로 부터받은 Token을 이용해 사용자 정보 요청하기**/
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
            httpResponse.setHeader("X-AUTH-TOKEN",token);

            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            httpResponse.addCookie(cookie);

            return "redirect:/";
        }


    }

}

