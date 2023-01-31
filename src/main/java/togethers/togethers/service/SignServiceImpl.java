package togethers.togethers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;
import togethers.togethers.service.SignService;


import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    private final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String nickname, String email, String birth, String phoneNum, String role){
        logger.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if(role.equalsIgnoreCase("admin")) {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .email(email)
                    .birth(birth)
                    .phoneNum(phoneNum)
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }else{
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .email(email)
                    .birth(birth)
                    .phoneNum(phoneNum)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getName().isEmpty())
        {
            logger.info("[getSignResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        }else {
            logger.info("[getSingUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        logger.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.getByUid(id);
        logger.info("[getSignInResult] Id : {}",id);

        logger.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException();
        }
        logger.info("[getSignResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()))
                .build();
        logger.info("[getSignInResult] getSignInResult 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;

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
