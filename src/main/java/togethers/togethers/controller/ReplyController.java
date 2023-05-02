package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.dto.reply.ReplyRequestDto;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;
import togethers.togethers.service.ReplyService;

import javax.servlet.http.HttpServletRequest;

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
    public String replyWrite(HttpServletRequest request,RedirectAttributes attr)
    {
        String postId = request.getParameter("p_id");
        long p_id = Long.parseLong(postId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser"){
            attr.addFlashAttribute("reply_msg","로그인 이후 댓글 작성이 가능합니다");
        }
        else{
            User user = (User)principal;
            logger.info("[replyWrite]  게시물 댓글 작성 로직 동작 postId:{}, userId:{} comment: {}",p_id,user.getUid(),request.getParameter("comment"));

            ReplyRequestDto dto = ReplyRequestDto.builder()
                    .postId(p_id)
                    .id(user.getId())
                    .comment(request.getParameter("comment"))
                    .build();
            replyService.Reply_write(dto);
        }


        return "redirect:/post/detailPost/"+p_id;
    }
}
