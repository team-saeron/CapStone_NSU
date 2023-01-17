package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.entity.MemberDetail;
import togethers.togethers.entity.Reply;
import togethers.togethers.form.MemberDetailForm;
import togethers.togethers.form.replyForm;
import togethers.togethers.service.MemberService;

import java.util.Date;

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
        MemberDetailForm memberDetailForm = new MemberDetailForm();
        model.addAttribute("MemberDetailForm", memberDetailForm);


        return "member/introduction";}

    //form에 담겨있는 data를 db에 전달해주는 역할
    @PostMapping("/member/introduction")
    public String introduction(MemberDetailForm memberDetailForm){

        MemberDetail memberDetail = new MemberDetail();

        memberDetail.setMbti(memberDetailForm.getMbti());
        memberDetail.setSelfIntro(memberDetailForm.getSelfIntro());
        memberDetail.setWish_roommate(memberDetailForm.getWish_roommate());
        memberDetail.setMouthly_fee(memberDetailForm.getMouthly_fee());
        memberDetail.setLease_fee(memberDetailForm.getLease_fee());

        //저장하는
        memberService.memberDetail(memberDetail);


        return "member/introduction";

    }


}

