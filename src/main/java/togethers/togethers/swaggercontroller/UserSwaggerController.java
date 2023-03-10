package togethers.togethers.swaggercontroller;

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
public class UserSwaggerController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserSwaggerController.class);

    private final UserService userService;

    @Autowired
    public UserSwaggerController(UserService userService){
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


}
