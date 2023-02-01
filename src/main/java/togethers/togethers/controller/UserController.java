package togethers.togethers.controller;

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
import togethers.togethers.data.dto.UserDetailSaveDto;
import togethers.togethers.data.dto.UserDetails;
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

//    public String getCurrentUser(Principal principal){
//        return principal.getName();
//    }

    @PostMapping(value="/introduction")
    public Long saveIntro(@RequestBody UserDetailSaveDto userDetailSaveDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails user = (UserDetails)authentication.getPrincipal().getUsername();
        User user = (User)authentication.getPrincipal();
        LOGGER.info("id = {}, name = {}", user.getUid(), user.getName());
        Long saveIntro = userService.saveIntro(user.getUid(), userDetailSaveDto);
//         userService.saveIntro(userDetailSaveDto);
        return saveIntro;
    }

//    @PostMapping("/updateIntroduction")
//    public String update(UserDetailUpdateDto dto){
//        UserDetail userDetail = dto.toEntity();
//        userDetailRepository.save(userDetail);
//    }
}
