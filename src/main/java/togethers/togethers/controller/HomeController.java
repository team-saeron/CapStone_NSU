package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.PostSearchDto;
import togethers.togethers.dto.RecentlyPostDto;
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

//    @GetMapping(value = "/test")
//    public void login_session() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//
//        if(principal =="anonymousUser"){
//            logger.info("[login_session] user is anonymousUser");
//        }else{
//            logger.info("[login_session] user not equals anonymousUser");
//        }
//
//    }
}
