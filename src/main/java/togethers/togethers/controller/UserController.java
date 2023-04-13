package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.*;
import togethers.togethers.entity.User;
import togethers.togethers.service.UserService;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }


    @GetMapping(value = "/introduction")
    public String saveIntro(Model model){

        log.info("[saveIntro] 사용자 세부사항 작성 GET 매핑 Controller 동작");
        UserDetailSaveDto dto = new UserDetailSaveDto();
        model.addAttribute("dto",dto);
        model.addAttribute("areaEnum", AreaEnum.values());
        return "member/introduction";
    }

    @PostMapping(value="/introduction")
    public String saveIntro(@Valid UserDetailSaveDto userDetailSaveDto){

        log.info("[saveIntro] 사용자 세부사항 작성 Post 매핑 Controller 동작");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        userService.saveIntro(user.getUid(), userDetailSaveDto);
        return "redirect:/";
    }

    @PatchMapping(value="/introduction/edit")
    public void editIntro(@Valid UserDetailUpdateDto userDetailUpdateDto){
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
        return "/login";
    }

    @PatchMapping("/user/editPassword")
    public void updatePassword(Long id, String password){
        userService.updatePassword(id,password);
    }
}


/**
 * 아이디 찾기, 비밀번호 찾기 페이지.**/