package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.service.UserService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LOGGER.info("id = {}, name = {}", authentication.getName());
        Long saveIntro = userService.saveIntro(authentication.getName(), userDetailSaveDto);

        return saveIntro;
    }


}
