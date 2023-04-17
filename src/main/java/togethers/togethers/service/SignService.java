package togethers.togethers.service;

import togethers.togethers.dto.SignInRequestDto;
import togethers.togethers.dto.SignInResultDto;
import togethers.togethers.dto.SignUpRequestDto;
import togethers.togethers.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(SignUpRequestDto signUpRequestDto);
    SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException;

    boolean idCheck(String Uid);
}
