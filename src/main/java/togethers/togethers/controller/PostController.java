package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.service.form.Postform;
import togethers.togethers.service.form.ReplyForm;
import togethers.togethers.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {


    private final MemberService memberService;
    @GetMapping("/write")
    public String postFormCreate(Model model, Postform postform){
        model.addAttribute("Postform",postform);
        return "write";
    }

    @PostMapping("/write")
    public String PostCreate(@Valid Postform postform, MultipartFile file)throws Exception
    {
//        Member member = memberService.findMember("akahd135");
//        Post post = new Post(postform);
//        memberService.post_write(post);
//        memberService.Member_Post(post,member.getId());
//        return "redirect:/";

        memberService.post_save(postform,file);
        return "redirect:/";
    }

    @GetMapping("/postlist")
    public String postlist()
    {
        return "post/postlist";
    }


    @GetMapping("/detailPost")
    public String detailPost(Model model)
    {
        Member member = memberService.findMember("akahd135"); //회원의 아이디를 조회
        ReplyForm replyForm = new ReplyForm();

        Long post_id = member.getPost().getPost_id();

        Post memberPost = memberService.findPost(post_id); //회원이 작성한 post를 memberPost에 받아옴

        model.addAttribute("post",memberPost);
        model.addAttribute("member",member);
        model.addAttribute("plyForm",replyForm);
        return "post/detailPost";

    }

//    @GetMapping("/findPost")
//    public String findPost(Model model)
//    {
//        Post findpost = memberService.findPost(1L);
//        model.addAttribute("post",findpost);
//        return "/test";
//    }





}