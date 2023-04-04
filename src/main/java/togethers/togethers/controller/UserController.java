package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.*;
import togethers.togethers.entity.User;
import togethers.togethers.service.UserService;

@RestController
@Slf4j
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }



    @PostMapping(value="/introduction")
    public Long saveIntro(@RequestBody UserDetailSaveDto userDetailSaveDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        LOGGER.info("name = {}, pw={}", user.getUid());
        Long saveIntro = userService.saveIntro(user.getUid(), userDetailSaveDto);
        return saveIntro;
    }

    @PatchMapping(value="/introduction/edit")
    public void editIntro(@RequestBody UserDetailUpdateDto userDetailUpdateDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        LOGGER.info("name = {}, pw={}", user.getUid());
        userService.editIntro(user.getId(), userDetailUpdateDto);
    }


    @PostMapping("/findId")
    public String findId(@RequestBody FindUserDto findUserDto){

        User user = userService.findId(findUserDto);
        log.info("[findId] 아이디 찾기를 시작합니다. user : {}", user.getUid());
        return user.getUid();
    }

    @PostMapping("/sendEmailPw")
    public String sendEmailPw(@RequestBody FindPassword findPassword){
        MailDto mailDto = userService.sendEmail(findPassword);
        userService.mailSend(mailDto);
        log.info("[sendEmailPw] 임시 비밀번호 전송 완료.");
        return "/sign-api/sign-in";
    }

    @PatchMapping("/user/editPassword")
    public void updatePassword(Long id, String password){
        userService.updatePassword(id,password);
    }
}


/**
 * 아이디 찾기, 비밀번호 찾기 페이지.**/