package togethers.togethers.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import togethers.togethers.data.dto.SignInRequestDto;
import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpRequestDto;
import togethers.togethers.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(SignUpRequestDto signUpRequestDto);
    SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException;
}
