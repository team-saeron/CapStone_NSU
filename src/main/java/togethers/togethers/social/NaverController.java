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
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.parser.ParserException;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpResultDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NaverController {

    private Logger logger = LoggerFactory.getLogger(NaverController.class);

    private final SocialService socialService;


    @Autowired
    public NaverController(SocialService socialService) {
        this.socialService = socialService;
    }

    @GetMapping(value = "/naver_callback")
    public String NaverSocialSignUp(String code, String state, RedirectAttributes attr)
    {
        logger.info("[NaverSocialLogin] 네이버 계정으로 회원가입 컨트롤러 동작");
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "TCaKGyOMHwb8QZ0oSMyV");
        params.add("client_secret", "k9iThEg12Q");
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naver_token_request =
                new HttpEntity<>(params,headers);


        ResponseEntity<String> response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naver_token_request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        NaverOAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), NaverOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> naver_profile_request =
                new HttpEntity<>(headers2);


        ResponseEntity<String> naver_profile_response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naver_profile_request,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        NaverProfile naverProfile = null;

        try {
            naverProfile = objectMapper.readValue(naver_profile_response.getBody(), NaverProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
    public String NaverLogin(String code,String state ,RedirectAttributes attr, HttpServletResponse httpResponse)
    {
        logger.info("[NaverLogin] 네이버 로그인 Controller 동작.");

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "TCaKGyOMHwb8QZ0oSMyV");
        params.add("client_secret", "k9iThEg12Q");
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naver_token_request =
                new HttpEntity<>(params,headers);


        ResponseEntity<String> response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naver_token_request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        NaverOAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), NaverOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> naver_profile_request =
                new HttpEntity<>(headers2);


        ResponseEntity<String> naver_profile_response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naver_profile_request,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        NaverProfile naverProfile = null;

        try {
            naverProfile = objectMapper.readValue(naver_profile_response.getBody(), NaverProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        SocialLoginDto socialLoginDto = SocialLoginDto.builder()
                .email(naverProfile.getResponse().getEmail())
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
