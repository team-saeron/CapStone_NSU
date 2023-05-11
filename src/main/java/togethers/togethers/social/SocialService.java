package togethers.togethers.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**카카오 계정 회원 가입 로직**/
    @Transactional
    public SignUpResultDto KakaoSignUp(KakaoProfile kakaoProfile) {
        logger.info("[KakaoSignUp] 네이버 로그인 Service 로직 동작 사용자 이메일 :{}", kakaoProfile.getKakao_account().getEmail());
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

    /** 소셜 계장 로그인 로직 **/

    @Transactional
    public SignInResultDto SocialLogin(SocialLoginDto socialLoginDto)
    {
        logger.info("[SocialLogin] Social 계정 정보로 로그인 요청 Service 동작 email:{}",socialLoginDto.getEmail());
        User user = userRepository.findByEmail(socialLoginDto.getEmail()).orElse(null);


        if(user == null){
            logger.info("일치하는 카카오 계정 정보가 없음 카카오 이메일: {}",socialLoginDto.getEmail());
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
