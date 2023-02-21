package togethers.togethers.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.UserDetailEditDto;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.dto.UserDetails;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.service.UserService;
import togethers.togethers.service.UserServiceImpl;

import java.security.Principal;

//@Controller
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
    public Long editIntro(@RequestBody UserDetailEditDto userDetailEditDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        LOGGER.info("name = {}, pw={}", user.getUid());
        Long editIntro = userService.editIntro(user.getUid(), userDetailEditDto);
        return editIntro;
    }

}
