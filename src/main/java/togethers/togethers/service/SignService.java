package togethers.togethers.service;

import togethers.togethers.data.dto.SignInResultDto;
import togethers.togethers.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String nickname, String email, /*Date birth, */String phoneNum, String role);
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
