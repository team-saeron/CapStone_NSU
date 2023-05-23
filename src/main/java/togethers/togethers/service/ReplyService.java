package togethers.togethers.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.dto.*;
import togethers.togethers.dto.reply.ReplyDeleteResultDto;
import togethers.togethers.dto.reply.ReplyRequestDto;
import togethers.togethers.dto.reply.ReplyResultDto;
import togethers.togethers.entity.Notification;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.User;
import togethers.togethers.repository.NotificationRepository;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.ReplyRepository;
import togethers.togethers.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final NotificationRepository notificationRepository;



    @Transactional(readOnly = false)
    public ReplyResultDto Reply_write(ReplyRequestDto dto)
    {
        logger.info("[Reply_write] 댓글 저장 로직 동작. user:{},post:{}, comment:{}",dto.getId(),dto.getPostId(),dto.getComment());
        Reply checkReply = replyRepository.findByPost_PostIdAndUser_Id(dto.getPostId(), dto.getId()).orElse(null);
        ReplyResultDto replyResultDto = new ReplyResultDto();

        if(checkReply == null)
        {
            Reply newReply = new Reply(dto);
            logger.info("[Reply_write] 새로운 댓글 작성 로직 동작.");
            User user = userRepository.findById(dto.getId()).orElse(null);
            User postUser = userRepository.findByPost_PostId(dto.getPostId()).orElse(null);
            Post post = postRepository.findById(dto.getPostId()).orElse(null);

            newReply.setUser(user);
            newReply.setPost(post);

            post.getReplies().add(newReply);
            replyRepository.save(newReply);

            /**댓글 작성 알림 컨트롤러 추가**/
            Notification notification = Notification.builder()
                    .title("댓글 알림")
                    .user(postUser)
                    .message(newReply.getUser().getNickname() + "님이 댓글을 달았습니다.")
                    .created(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
            setSuccessResult(replyResultDto);
            replyResultDto.setCode(1);
        }else{
            logger.info("[Reply_write] 댓글 수정 로직 동작.");
            checkReply.setComment(dto.getComment());

            replyRepository.flush();
            setSuccessResult(replyResultDto);
            replyResultDto.setCode(2);
        }

        return replyResultDto;
    }

    @Transactional
    public ReplyDeleteResultDto replyDelete(Long postId, Long userId, Long replyId)
    {
        ReplyDeleteResultDto replyDeleteResultDto = new ReplyDeleteResultDto();
        replyDeleteResultDto.setUserId(userId);
        replyDeleteResultDto.setReplyId(replyId);

        logger.info("[reply_delete] 댓글 삭제 로직 동작. postId:{}, userId:{}",postId,userId);
        Reply reply = replyRepository.findById(replyId).orElse(null);

        if(!userId.equals(reply.getUser().getId()))
        {
            logger.info("[reply_delete] 사용자가 삭제할수 없는 댓글입니다");
            setFailResult(replyDeleteResultDto);
            replyDeleteResultDto.setMsg("사용자가 삭제할수 없는 댓글입니다");
            return replyDeleteResultDto;
        }
        else {
            replyRepository.deleteByPost_PostIdAndUser_Id(postId,userId);
            setSuccessResult(replyDeleteResultDto);
        }
        return replyDeleteResultDto;
    }

    private void setSuccessResult(BaseResultDto result)
    {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(BaseResultDto result)
    {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }



}
