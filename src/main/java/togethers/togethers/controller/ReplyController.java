package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import togethers.togethers.dto.ReplyDeleteResultDto;
import togethers.togethers.dto.ReplyRequestDto;
import togethers.togethers.dto.ReplyResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.service.ReplyService;

import java.util.Date;

@RestController
@Slf4j
public class ReplyController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);


    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService)
    {
        this.replyService = replyService;
    }



    @PostMapping("/detailPost/Reply") ///detailPost/{post_id}/Reply로 변경 매개변수는 @Pathvariable로 변경
    public ReplyResultDto Reply_write(@RequestParam(value = "post_id")Long post_id,
                                      @RequestParam(value = "comment")String comment)
    {
        LOGGER.info("[Reply_Write] Controller 동작. 게시물 Id: {}",post_id);

        ReplyRequestDto replyRequestDto = ReplyRequestDto.builder()
                                          .comment(comment).
                                          PublishedDate(new Date()).build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        User user = (User)principal;
        LOGGER.info("User Uid:{}",user.getUid());

        ReplyResultDto replyResultDto = replyService.Reply_write(user.getId(), post_id, replyRequestDto);

        return replyResultDto;
    }

    @PostMapping(value = "/detailPost/ReplyDelete")
    public ReplyDeleteResultDto Reply_delete(Long PostId)
    {
        LOGGER.info("[Reply_delete] 댓글 삭제 컨트롤러 동작. 게시물 Id:{}",PostId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        ReplyDeleteResultDto replyDeleteResultDto = replyService.reply_delete(PostId, user.getId());
        return replyDeleteResultDto;
    }
}
