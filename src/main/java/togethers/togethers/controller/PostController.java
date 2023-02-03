package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;
import togethers.togethers.service.UserServiceImpl;
import togethers.togethers.service.form.Postform;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private UserServiceImpl userServiceImpl;


    private final PostService postService;
    @GetMapping("/write")
    public String postFormCreate(Model model, Postform postform){
        model.addAttribute("Postform",postform);
        return "write";
    }

    @PostMapping("/write")
    public String PostCreate(@Valid Postform postform, MultipartFile file)throws Exception
    {
//        Member member = memberService.findMember("akahd135");
////        Post post = new Post(postform);
////        memberService.post_write(post);
////        memberService.Member_Post(post,member.getId());
////        return "redirect:/";

//        Member member = memberService.findMember("akahd135");

        String memberid = "akahd135";
        postService.post_save(postform,file);

        return "redirect:/";
    }

    @GetMapping("/postlist")
    public String postlist()
    {
        return "post/postlist";
    }


//    @GetMapping("/detailPost")
//    public String detailPost(Model model)
//    {
//        User user = userServiceImpl.findMember("akahd135");//회원의 아이디를 조회
//
//
//        Long post_id = user.getPost().getPost_id();
//
//
//        //회원이 작성한 post를 memberPost에 받아옴
////        Post memberPost = memberService.findPost(post_id);
//
////        model.addAttribute("post",memberPost);
//        model.addAttribute("member",user);
//
//        return "post/detailPost";
//
//    }

//    @GetMapping("/findPost")
//    public String findPost(Model model)
//    {
//        Post findpost = memberService.findPost(1L);
//        model.addAttribute("post",findpost);
//        return "/test";
//    }





}