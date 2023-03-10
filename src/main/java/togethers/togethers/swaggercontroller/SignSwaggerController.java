package togethers.togethers.swaggercontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.SignInRequestDto;
import togethers.togethers.dto.SignInResultDto;
import togethers.togethers.dto.SignUpRequestDto;
import togethers.togethers.dto.SignUpResultDto;
import togethers.togethers.service.SignService;
import togethers.togethers.service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sign-api")
@RequiredArgsConstructor
@Slf4j
public class SignSwaggerController {
    private final Logger LOGGER = LoggerFactory.getLogger(SignSwaggerController.class);
    private final UserServiceImpl userServiceImpl;

    private final SignService signService;


    @PostMapping(value="sign-up"/*, consumes = "application/x-www-form-urlencoded"*/)
    public String signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}, password : {}, email : {}, birth : {}, nickname : {}, phoneNum : {}",signUpRequestDto.getId(), signUpRequestDto.getName(),signUpRequestDto.getRole(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getNickname(), /*signUpRequestDto.getBirth(),*/ signUpRequestDto.getPhoneNum());
        SignUpResultDto signUpResultDto = signService.signUp(signUpRequestDto);
        if(signUpResultDto.getCode()==0)
            LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", signUpRequestDto.getId());
        return "home";
    }


    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@RequestBody SignInRequestDto signInRequestDto)throws RuntimeException{
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", signInRequestDto.getId());
        SignInResultDto signInResultDto = signService.signIn(signInRequestDto);

        if(signInResultDto.getCode()==0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", signInRequestDto.getId(), signInResultDto.getToken());
            return signInResultDto;
        }
        return signInResultDto;
    }




    @GetMapping(value="/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<Map<String, String>> handle(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
