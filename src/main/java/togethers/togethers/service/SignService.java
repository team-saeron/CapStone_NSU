package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpResultDto;

import java.util.Date;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String nickname, String email, String birth, String phoneNum, String role);
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
