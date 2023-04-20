package togethers.togethers.service;

import togethers.togethers.dto.login.SignInRequestDto;
import togethers.togethers.dto.login.SignInResultDto;
import togethers.togethers.dto.login.SignUpRequestDto;
import togethers.togethers.dto.login.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(SignUpRequestDto signUpRequestDto);
    SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException;

    boolean idCheck(String Uid);
}
