package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpResultDto;

import java.util.Date;

@Service
public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String nickname, String email, Date birth, String phoneNum, String roles);
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
