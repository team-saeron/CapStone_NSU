package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import togethers.togethers.dto.DetailPostDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.service.PostService;

import java.util.List;

@Controller
public class TestController {


    private final PostService postService;

    @Autowired
    public TestController(PostService postService)
    {
        this.postService = postService;
    }
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "post/postList")
    public String post_poostList(@PageableDefault Pageable pageable, Model model){
        logger.info("[postList]게시물 목록 조회 controller 동작");

        Page<Post>postList = postService.post_postList(pageable);

        model.addAttribute("postList",postList);
        logger.info("[postList] 총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                postList.getTotalElements(), postList.getTotalPages(), postList.getSize(),
                postList.getNumber(), postList.getNumberOfElements());

        return "post/postList";

    }

    @GetMapping(value = "/post/detailPost/{postId}")
    public String post_detailPost(@PathVariable("postId")Long PostId, Model model)
    {
        System.out.println("************** 게시물 id:"+PostId+"***************");
        logger.info("[post_detailPost] 게시물 세부사항 관련 로직 동작 PostId:{}",PostId);

        Post post = postService.findPost(PostId);
        RoomPicture photo = postService.findPhoto(PostId);
        List<Reply> replies = postService.findReply(PostId);

        DetailPostDto detailPostDto = postService.detail_post(post, photo, replies);


        model.addAttribute("post",detailPostDto);

        return "post/detailPost";
    }
}
