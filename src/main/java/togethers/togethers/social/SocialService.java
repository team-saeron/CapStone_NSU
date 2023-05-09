package togethers.togethers.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;

@Service
public class SocialService {
    // Social 로그인한 계정도 JWT token읋 발급해줘야함
    // Social 받아온 KakaoProfile로 user를 생성헤줘야함
    private Logger logger = LoggerFactory.getLogger(SocialService.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;



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

    public SignInResultDto kakaoLogin(SocialLoginDto socialLoginDto)
    {
        logger.info("[KakaoLogin] 카카오 계정 정보로 로그인 요청 Service 동작 email:{}",socialLoginDto.getEmail());
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
