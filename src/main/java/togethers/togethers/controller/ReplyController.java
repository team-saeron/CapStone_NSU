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

        String postId = request.getParameter("p_id");
        long p_id = Long.parseLong(postId);

        Post post = postService.findPost(p_id);
        RoomPicture photo = postService.findPhoto(p_id);
        List<Reply> replies = postService.findReply(p_id);
        DetailPostDto detailPostDto = postService.detail_post(post, photo);
        boolean check;

        model.addAttribute("post",detailPostDto);
        model.addAttribute("replies",replies);
        model.addAttribute("postId",p_id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser"){
            logger.info("[replyWrite] 사용자가 로그인 하지 않아 게시물 작성 불가");
            check = false;
            model.addAttribute("reply_msg","로그인 이후 댓글 작성이 가능합니다.");
            model.addAttribute("userId"," ");
            model.addAttribute("check",check);
        }else{
            User user = (User)principal;
            logger.info("[replyWrite]  게시물 댓글 작성 로직 동작 postId:{}, userId:{} comment: {}",p_id,user.getUid(),request.getParameter("comment"));

            ReplyRequestDto dto = ReplyRequestDto.builder()
                    .postId(p_id)
                    .id(user.getId())
                    .comment(request.getParameter("comment"))
                    .build();
            replyService.Reply_write(dto);
            check = postService.checkFavorite(p_id, user.getId());

            model.addAttribute("check",check);
            model.addAttribute("userId",user.getUid());
        }


        return "post/detailPost";
    }
}
