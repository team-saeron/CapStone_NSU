package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.*;
import togethers.togethers.entity.User;
import togethers.togethers.service.SignService;
import togethers.togethers.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final SignService signService;

    @Autowired
    public UserController(UserService userService, SignService signService){
        this.userService=userService;
        this.signService = signService;
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


    @GetMapping("/findId")
    public String findId(Model model){
        return "find/choose";
    }




    /************************ 핸드폰 번호로 ID 찾기 GET, POST controller *************************/
    @GetMapping("/findId/phone")
    public String findIdByPhoneNum(){
        log.info("[findIdByPhoneNum] 핸드폰 번호를 이용한 아이디 찾기 GET 매핑 동작");
        return "find/phone";
    }


    @PostMapping("/findId/phone")
    public String findIdByPhoneNum(HttpServletRequest request, RedirectAttributes attr){
        log.info("[findIdByPhoneNum] 핸드폰 번호를 이용한 아이디 찾기 POST 매핑 동작 name : {}, phoneNum : {}",request.getParameter("name"),request.getParameter("phoneNum"));
        FindIdPhoneDto findIdPhoneDto = new FindIdPhoneDto(request.getParameter("name"),request.getParameter("phoneNum"));
        User user = userService.findIdByPhoneNum(findIdPhoneDto);
        if(user == null){
            attr.addFlashAttribute("NotFindUser","일치 하는 정보가 없습니다");
            return "redirect:/findId/phone";
        }else {
            attr.addFlashAttribute("findUserId","회원님의 아이디는 "+user.getUid()+" 입니다.");
            return "redirect:/login";

        }

    }



    /********************* 이메일로 ID 찾기 GET, POST controller ****************************/
    @GetMapping("/findId/mail")
    public String findIdByEmail()
    {
        log.info("[findIdByEmail] 아이디 이메일로 찾기 GET controller 동작");
        return "find/mail";
    }

    @PostMapping("/findId/mail")
    public String findIdByEmail(HttpServletRequest request, RedirectAttributes attr)
    {
        log.info("[findIdByEmail] 이메일로 아이디 찾기 POST controller 동작 name: {} , email : {}",request.getParameter("name"),request.getParameter("email"));
        FindIdEmailDto findIdEmailDto = new FindIdEmailDto(request.getParameter("name"),request.getParameter("email"));
        User user = userService.findIdByEmail(findIdEmailDto);
        if(user == null)
        {
            attr.addFlashAttribute("NotFindUser","일치하는 정보를 찾을수 없습니다");
            return "redirect:/findId/mail";
        }else{
            attr.addFlashAttribute("findUserId","회원님의 아이디는 "+user.getUid()+" 입니다.");
            return "redirect:/login";

        }
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "find/password";
    }

    @PostMapping("/findPassword")
    public String sendEmailPw(HttpServletRequest request, RedirectAttributes attr){
        log.info("[sendEmailPw] 비밀번호 찾기 POST controlloer 동작. name :{}, email :{}, id: {}"
                ,request.getParameter("name")
                ,request.getParameter("email"),
                request.getParameter("id"));

        FindPasswordDto findPasswordDto = new FindPasswordDto(request.getParameter("name")
                ,request.getParameter("email"),
                request.getParameter("id"));

        MailDto mailDto = userService.sendEmail(findPasswordDto);
        if(mailDto == null)
        {
            log.info("[sendEmailPw] DB 조회결과 일치하는 사용자 정보 없음 ");
            attr.addFlashAttribute("NotFindUser", "일치하는 정보를 찾을수 없습니다");
            return "redirect:/findPassword";
        }else {
            userService.mailSend(mailDto);
            log.info("[sendEmailPw] 임시 비밀번호 전송 완료.");
            attr.addFlashAttribute("SendTempPassword", "임시 비밀번호를 이메일로 전송하였습니다 확인하여주세요.");
            return "redirect:/login";
        }
    }

    @PatchMapping("/user/editPassword")
    public void updatePassword(Long id, String password){
        userService.updatePassword(id,password);
    }
}


