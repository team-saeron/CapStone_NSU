package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.form.Postform;
import togethers.togethers.service.MemberService;
import togethers.togethers.test.Album;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public String PostCreate(@Valid Postform postform)
    {
        Post post = new Post(postform);
        memberService.post_write(post);
        return "redirect:/";
    }

    @GetMapping("/postlist")
    public String postlist()
    {
        return "post/postlist";
    }


    @GetMapping("/detailPost")
    public String detailPost()
    {
        return "post/detailPost";
    }

    @GetMapping("/test")
    public void test_save()
    {



    }

    public void comp(Member m1, Member m2){

        System.out.println(m1 instanceof Member);
        System.out.println(m2 instanceof Member);
    }
}