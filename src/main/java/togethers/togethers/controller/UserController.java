package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.FindUserDto;
import togethers.togethers.dto.UserDetailUpdateDto;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.entity.User;
import togethers.togethers.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
