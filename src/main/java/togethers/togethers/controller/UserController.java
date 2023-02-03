package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.entity.User;
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

//    @GetMapping("/introduction")
//    public String getCurrentUser(Principal principal){
//        return principal.getName();
//    }

    @PostMapping(value="/introduction")
    public Long saveIntro(@RequestBody UserDetailSaveDto userDetailSaveDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails user = (UserDetails)authentication.getPrincipal().getUsername();
        User user = (User)principal;
        LOGGER.info("name = {}, pw={}", user.getUid());
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
