package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import togethers.togethers.dto.DetailPostDto;
import togethers.togethers.dto.ReplyRequestDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;
import togethers.togethers.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ReplyController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final ReplyService replyService;

    @Autowired
    public ReplyController(PostService postService, ReplyService replyService)
    {
        this.postService = postService;
        this.replyService = replyService;
    }


    @PostMapping(value = "/post/detailPost/reply")
    public String replyWrite(HttpServletRequest request, Model model)
    {
        logger.info("댓글 작성 ReplyController  postId :  {},comment: {}",request.getParameter("p_id"),request.getParameter("comment"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        String postId = request.getParameter("p_id");
        long p_id = Long.parseLong(postId);


        ReplyRequestDto dto = ReplyRequestDto.builder()
                .postId(p_id)
                .id(user.getId())
                .comment(request.getParameter("comment"))
                .build();

        replyService.Reply_write(dto);


        /** 댓글 작성했던 게시물로 리다이렉트 하기위해 detailPost에 필요한 DTO들 찾기**/
        Post post = postService.findPost(p_id);
        RoomPicture photo = postService.findPhoto(p_id);
        List<Reply> replies = postService.findReply(p_id);
        DetailPostDto detailPostDto = postService.detail_post(post, photo);

        model.addAttribute("post",detailPostDto);
        model.addAttribute("user",user);
        model.addAttribute("replies",replies);
        model.addAttribute("postId",p_id);



        return "post/detailPost";
    }
}
