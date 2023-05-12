package togethers.togethers.social;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.imap.AppendUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import togethers.togethers.Enum.SocialName;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;

import java.sql.Date;
import java.util.Collections;

@Service
public class SocialService {
    // Social 로그인한 계정도 JWT token읋 발급해줘야함
    // Social 받아온 KakaoProfile로 user를 생성헤줘야함
    private Logger logger = LoggerFactory.getLogger(SocialService.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;


    @Value("${social.key}")
    private String key;

    @Autowired
    public SocialService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public void saveSocialUser(User user)
    {
        userRepository.save(user);
    }


    /**카카오로부터 카카오 계정 정보 얻는 로직.**/
    public KakaoProfile getKakaoProfile(String code)
    {
        logger.info("[getKakaoProfile] 카카오 서버로 부터 카카오 계정 정보 얻는 로직 수행");
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
        return kakaoProfile;
    }

    /**네이버로 부터 네이버 계정 정보 얻는 메서드**/
    public NaverProfile getNaverProfile(String code, String state)
    {
        logger.info("[getNaverProfile] 네이버 서버로부터 네이버 계정 정보 얻는 로직 시작.");
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

        return naverProfile;
    }

    /** 소셜 계정 로그인 로직 **/
    @Transactional
    public SignInResultDto SocialLogin(SocialLoginDto socialLoginDto)
    {
        logger.info("[SocialLogin] Social 계정 정보로 로그인 요청 Service 동작 email:{}",socialLoginDto.getEmail());
        User user = userRepository.findByEmail(socialLoginDto.getEmail()).orElse(null);


        if(user == null){
            logger.info("일치하는 계정 정보가 없음 카카오 이메일: {}",socialLoginDto.getEmail());
            SignInResultDto failDto = SignInResultDto.builder().build();
            failDto.setCode(-1);
            return failDto;

        }
        else
        {
            SignInResultDto signInResultDto = SignInResultDto.builder()
                    .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
                    .build();
            setSuccessResult(signInResultDto);
            return signInResultDto;
        }
    }

    /**카카오 계정 회원 가입 로직**/
    @Transactional
    public SignUpResultDto KakaoSignUp(KakaoProfile kakaoProfile) {
        logger.info("[KakaoSignUp] 카카오 로그인 Service 로직 동작 사용자 이메일 :{}", kakaoProfile.getKakao_account().getEmail());
        User user = userRepository.findByEmail(kakaoProfile.getKakao_account().getEmail()).orElse(null);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if (user != null) {
            logger.info("[KakaoSignUp] {}은 이미 존재하는 이메일. 회원가입 불가", kakaoProfile.getKakao_account().getEmail());
            setFailResult(signUpResultDto);
            return signUpResultDto;
        } else {
            User kakao_user = User.builder()
                    .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                    .nickname(kakaoProfile.getKakao_account().getProfile().getNickname())
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .uid(kakaoProfile.getKakao_account().getEmail())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .password(passwordEncoder.encode(key))
                    .socialName(SocialName.KAKAO)
                    .build();
            userRepository.save(kakao_user);
            setSuccessResult(signUpResultDto);
            return signUpResultDto;
        }
    }

    /**네이버 계정 회원가입 로직**/
    @Transactional
    public SignUpResultDto NaverSignUp(NaverProfile naverProfile)
    {
        logger.info("[NaverSignUp] 네이버 로그인 Service 로직 동작 사용자 이메일 :{}",naverProfile.getResponse().getEmail());
        User user = userRepository.findByEmail(naverProfile.getResponse().getEmail()).orElse(null);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if(user != null)
        {
            logger.info("[NaverSignUp] {}은 이미 존재하는 이메일. 회원가입 불가",naverProfile.getResponse().getEmail());
            setFailResult(signUpResultDto);
            return signUpResultDto;
        }else{
            User naver_user = User.builder()
                    .uid(naverProfile.getResponse().getEmail())
                    .name(naverProfile.getResponse().getName())
                    .nickname(naverProfile.getResponse().getNickname())
                    .email(naverProfile.getResponse().getEmail())
                    .phoneNum(naverProfile.getResponse().getMobile())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .password(passwordEncoder.encode(key))
                    .socialName(SocialName.NAVER)
                    .build();
            String str = naverProfile.getResponse().getBirthyear()+"-"+naverProfile.getResponse().getBirthday();
            Date birth = Date.valueOf(str);
            naver_user.setBirth(birth);

            userRepository.save(naver_user);
            setSuccessResult(signUpResultDto);
            return signUpResultDto;
        }
    }





    private void setSuccessResult(SignUpResultDto result)
    {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result)
    {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
