package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.form.UserDetailForm;
import togethers.togethers.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private final MemberService memberService;


    @GetMapping("/member/mypage")
    public String mypage(Model model){
        return "member/mypage";}

//    @GetMapping("/test")
//    public String test(Model model) {
//        replyForm replyForm = new replyForm();
//        model.addAttribute("ReplyForm", replyForm);
//        return "test";
//    }
//
//    @PostMapping("/detailPost")
//    public String Test(Model model) {
//        Reply reply = new Reply();
//        reply.setComment(reply.getComment());
//        reply.setComment_publishedDate(new Date());
//        return "/post/detailPost";
//
//    }

    @GetMapping("/member/introduction")
    public String introduction(Model model){
        UserDetailForm userDetailForm = new UserDetailForm();
        model.addAttribute("MemberDetailForm", userDetailForm);


        return "member/introduction";}

    //form에 담겨있는 data를 db에 전달해주는 역할
    @PostMapping("/member/introduction")
    public String introduction(UserDetailForm userDetailForm){

        UserDetail userDetail = new UserDetail();

        userDetail.setMbti(userDetailForm.getMbti());
        userDetail.setSelfIntro(userDetailForm.getSelfIntro());
        userDetail.setWish_roommate(userDetailForm.getWish_roommate());
        userDetail.setMonthly_fee(userDetailForm.getMouthly_fee());
        userDetail.setLease_fee(userDetailForm.getLease_fee());

        //저장하는
        memberService.UserDetail(userDetail);


        return "member/introduction";

    }


}

