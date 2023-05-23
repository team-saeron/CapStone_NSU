package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.post.PostSearchDto;
import togethers.togethers.dto.post.RecentlyPostDto;
import togethers.togethers.dto.post.RecommendPostDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;
import togethers.togethers.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public HomeController(PostService postService,UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping(value = "/")
    public String Home(Model model)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal == "anonymousUser")
        {
            model.addAttribute("login_inform",false);
        }
        else
        {
            User user = (User)principal;

            if(user.getUserDetail() == null)
            {
                logger.info("userDetail is null!!!");
                model.addAttribute("userDetail" , false);
                model.addAttribute("no_userdetail","나를 소개하는 글을 작성해 주세요!");
            }
            else if(user.getUserDetail() != null)
            {
                model.addAttribute("userDetail" , true);
                List<RecommendPostDto>recommendPostDto = new ArrayList<>();
                List<Post>matching = userService.matching(user.getUid());
                if (matching.size()>=4)
                {
                    for (Post post : matching)
                    {
                        logger.info("[homeController] 홈 컨트롤러에서 recommendDto에 matching 게시물 저장 시도.");
                        recommendPostDto.add(new RecommendPostDto(post));
                    }
                    model.addAttribute("recommendSize" , true);
                    model.addAttribute("recommendPostDto",recommendPostDto);
                }else {
                    model.addAttribute("recommendSize" , false);

                }

                logger.info("추천 게시물 갯수 : {} ",recommendPostDto.size());

            }


            if(user.getPost() == null)
            {
                model.addAttribute("postId",0);
            }else {
                model.addAttribute("postId",user.getPost().getPostId());
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

    @GetMapping(value="/kakaoMap")
    public String map(){
        return "kakaoMap";
    }



}
