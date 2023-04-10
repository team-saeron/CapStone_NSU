package togethers.togethers.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.SignInRequestDto;
import togethers.togethers.dto.SignInResultDto;
import togethers.togethers.dto.SignUpRequestDto;
import togethers.togethers.dto.SignUpResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;


import java.util.Collections;

@Service
@Slf4j
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
    public SignUpResultDto signUp(SignUpRequestDto signUpRequestDto){
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        logger.info("[getSignUpResult] 회원 가입 정보 전달");

        String tempEmail = signUpRequestDto.getEmail()+"@"+signUpRequestDto.getDomain();
        User user;
        if(signUpRequestDto.getRole().equals("admin")) {
            user = User.builder()
                    .uid(signUpRequestDto.getId())
                    .name(signUpRequestDto.getName())
                    .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                    .nickname(signUpRequestDto.getNickname())
                    .email(tempEmail)
                    .birth(signUpRequestDto.getBirth())
                    .phoneNum(signUpRequestDto.getPhoneNum())
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }else{
            user = User.builder()
                    .uid(signUpRequestDto.getId())
                    .name(signUpRequestDto.getName())
                    .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                    .nickname(signUpRequestDto.getNickname())
                    .email(tempEmail)
                    .birth(signUpRequestDto.getBirth())
                    .phoneNum(signUpRequestDto.getPhoneNum())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);

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
    public SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException {
        logger.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.findByUid(signInRequestDto.getId()).orElse(null);
        logger.info("[getSignInResult] Id : {}",signInRequestDto.getId());

        logger.info("[getSignInResult] 패스워드 비교 수행");

        if (user == null){
            logger.info("[signIn] 일치하는 아이디가 없음.");
            SignInResultDto failDto = SignInResultDto.builder().build();
            failDto.setCode(-1);
            return failDto;

        }

        else if(!passwordEncoder.matches(signInRequestDto.getPassword(),user.getPassword())){
            logger.info("[signIn] 사용자 비밀번호가 일치 하지 않음.");
            SignInResultDto failDto = SignInResultDto.builder().build();
            failDto.setCode(-2);
            return failDto;
        }
        else{
            logger.info("[getSignResult] 패스워드 일치");

            logger.info("[getSignInResult] SignInResultDto 객체 생성");

            SignInResultDto signInResultDto = SignInResultDto.builder()
                    .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
                    .build();

            logger.info("[getSignInResult] getSignInResult 객체에 값 주입");
            setSuccessResult(signInResultDto);

            return signInResultDto;
        }
    }


    @Override
    public int idCheck(String Uid)
    {
        User user = userRepository.findByUid(Uid).orElse(null);
        if(user == null)
        {
            return 0;
        }else{
            return 1;
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
