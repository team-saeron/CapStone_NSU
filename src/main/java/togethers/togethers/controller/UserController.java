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
import togethers.togethers.dto.login.*;
import togethers.togethers.dto.mypage.CheckIntroductionDto;
import togethers.togethers.dto.mypage.MyPageDto;
import togethers.togethers.dto.mypage.UserDetailSaveDto;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
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

    /************************ 마이 페이지 관련 GET, POST 매핑********************/
    @GetMapping(value = "/member/mypage")
    public String myPage(Model model, RedirectAttributes attr)
    {
        log.info("[myPage] 마이 페이지 관련 로직 동작.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser")
        {
            attr.addFlashAttribute("msg", "로그인 이후 사용 가능 합니다.");
            return "redirect:/";
        }else {
            User user = (User)principal;

            log.info("@@@@ {}, {}, {}",user.getNickname(),user.getUid(),user.getSocialName().name());
            MyPageDto myPageDto = new MyPageDto(user.getNickname(),user.getUid(),user.getSocialName().name());
            PasswordUpdatedDto passwordUpdatedDto = new PasswordUpdatedDto();

            model.addAttribute("passwordupdatedto",passwordUpdatedDto);
            model.addAttribute("dto",myPageDto);
            return "member/mypage";
        }
    }







    /********************** 나를 소개하는 페이지 작성 관련 GET, POST Controller********************/
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

    @GetMapping(value = "/member/rewriteIntroduction")
    public String rewriteIntroduction (Model model)
    {
        log.info("[rewriteIntroduction] 나의 게시물 수정 Controller 동작.");
        return "member/rewriteIntroduction";
    }

    @GetMapping("/member/checkIntroduction")
    public String checkIntroduction(@RequestParam("userDetailId")Long userDetailId,@RequestParam("postId")Long post_id ,Model model, RedirectAttributes attr)
    {
        log.info("[checkIntroduction] 다른 유저의 세부사항 정보 확인. 조회하고자 하는 Id : {}",userDetailId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser")
        {
            log.info("[checkIntroduction] 사용자가 로그인하지 않아 다른 사람의 자기소개 볼수 없음");
            attr.addFlashAttribute("no_login","로그인 이후 다른 사용자의 자기소개글을 볼 수 있습니다");
            return "redirect:/post/detailPost/"+post_id;
        }
        else{
            User reply_user = userService.findUserByUserDetailId(userDetailId);
            UserDetail reply_user_detail = reply_user.getUserDetail();

            CheckIntroductionDto checkIntroductionDto = CheckIntroductionDto.builder()
                    .nickname(reply_user.getNickname())
                    .gender(reply_user_detail.getGender())
                    .area(reply_user_detail.getRegions())
                    .room_type("월세")
                    .deposit(reply_user_detail.getLease_fee())
                    .month_fee(reply_user_detail.getMonthly_fee())
                    .mbti(reply_user_detail.getMbti())
                    .pet(reply_user_detail.getPet())
                    .smoking(reply_user_detail.getSmoking())
                    .life_cycle(reply_user_detail.getLife_cycle())
                    .wish_roommate(reply_user_detail.getWish_roommate())
                    .build();
            model.addAttribute("dto",checkIntroductionDto);
            return "member/checkIntroduction";

        }
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
        User user = userService.findUserByIdAndPhoneNum(findIdPhoneDto);
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
        User user = userService.findUserByEmail(findIdEmailDto);
        if(user == null)
        {
            attr.addFlashAttribute("NotFindUser","일치하는 정보를 찾을수 없습니다");
            return "redirect:/findId/mail";
        }else{
            attr.addFlashAttribute("findUserId","회원님의 아이디는 "+user.getUid()+" 입니다.");
            return "redirect:/login";

        }
    }




    /************************ 비밀번호 찾기 GET, POST 매핑 *********************/
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

    @PostMapping("/editPassword")
    public String updatePassword(RedirectAttributes attr, @Valid PasswordUpdatedDto passwordUpdatedDto){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        log.info("[updatePassword] 비밀 번호 변경 POST controller 동작. user ID :{}",user.getUid());
        passwordUpdatedDto.setUid(user.getUid());

        log.info("password_1 : {}. password: {}",passwordUpdatedDto.getNewPassword(),passwordUpdatedDto.getNewPasswordCheck());
        if(!passwordUpdatedDto.getNewPassword().equals(passwordUpdatedDto.getNewPasswordCheck()))
        {
            log.info("두개의 비밀번호 값이 다름 password_1 : {}. password_2: {}",passwordUpdatedDto.getNewPassword(),passwordUpdatedDto.getNewPasswordCheck());
            attr.addFlashAttribute("NotMatchNewPassword","새로 입력한 비밀번호와 확인 칸에 입력된 비밀번호가 다릅니다");
            return "redirect:/member/mypage";
        }

        if (!userService.updatePassword(passwordUpdatedDto))
        {
            attr.addFlashAttribute("NotMatchOriginPassword", "비밀번호가 일치하지 않습니다");
            return "redirect:/member/mypage";
        }
        else {
            attr.addFlashAttribute("UpdatedSuccess","비밀번호 변경이 성공적으로 수행되었습니다");
            return "redirect:/member/mypage";
        }

    }





}


