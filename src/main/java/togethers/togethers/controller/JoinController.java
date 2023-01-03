package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.entity.Member;
import togethers.togethers.form.MemberForm;
import togethers.togethers.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "join";
    }


    @PostMapping("/members/new")
    public String create(@Valid MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        member.setNickname(form.getNickname());
        member.setPhoneNum(form.getPhoneNum());
        member.setBirth(form.getBirth());
        member.setId(form.getId());

        memberService.join(member);
        return "redirect:/";


    }



    }

