package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.post.PostSearchDto;
import togethers.togethers.dto.post.RecentlyPostDto;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping(value = "/")
    public String Home(Model model)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal == "anonymousUser"){
            model.addAttribute("login_inform",false);
        }else{
            User user = (User)principal;
            if(user.getUserDetail() == null)
            {
                model.addAttribute("no_userdetail","나를 소개하는 글을 작성해 주세요!");
            }
            model.addAttribute("login_inform",true);
        }

        PostSearchDto postSearchDto = new PostSearchDto();

        List<RecentlyPostDto> recentlyPost = postService.RecentlyPost();

        for (RecentlyPostDto recentlyPostDto : recentlyPost) {
            System.out.println("날짜 값: "+recentlyPostDto.getDate());
        }

        model.addAttribute("recentlyPost",recentlyPost);
        model.addAttribute("dto",postSearchDto);
        model.addAttribute("category",AreaEnum.values());
        return "home";
    }
}
