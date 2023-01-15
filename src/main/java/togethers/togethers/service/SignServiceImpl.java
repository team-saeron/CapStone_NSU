package togethers.togethers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.config.PasswordEncoderConfiguration;
import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpResultDto;
import togethers.togethers.entity.Member;
import togethers.togethers.memberRepository.MemberRepository;

import java.util.Collections;
import java.util.Date;

@Service
public class SignServiceImpl implements SignService{
    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public MemberRepository memberRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String nickname, String email, Date birth,String phoneNum, String role){
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Member member;
        if(role.equalsIgnoreCase("admin")) {
            member = Member.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                            .build();
        }else{
            member = Member.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        Member savedMember = memberRepository.save(member);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if(!savedMember.getName().isEmpty()){
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        }else{
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
        }

        @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException{
        LOGGER.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        Member member = memberRepository.getByUid(id);
        LOGGER.info("[getSignInResult] Id : {}", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(member.getUid()),
                        member.getRoles()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);
        return signInResultDto;
        }

        private void setSuccessResult(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
        }

        private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
        }
}
