package togethers.togethers.service;


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
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.User;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.ReplyRepository;
import togethers.togethers.repository.UserRepository;

@Service
public class ReplyService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public ReplyService(ReplyRepository repository, UserRepository userRepository, PostRepository postRepository) {
        this.replyRepository = repository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = false)
    public ReplyResultDto Reply_write(ReplyRequestDto dto)
    {
        logger.info("[Reply_write] 댓글 저장 로직 동작. user:{},post:{}, comment:{}",dto.getId(),dto.getPostId(),dto.getComment());
        Reply check_reply = replyRepository.findByPost_PostIdAndUser_Id(dto.getPostId(), dto.getId()).orElse(null);
        ReplyResultDto reply_result_dto = new ReplyResultDto();

        if(check_reply == null)
        {
            Reply new_reply = new Reply(dto);
            logger.info("[Reply_write] 새로운 댓글 작성 로직 동작.");
            User user = userRepository.findById(dto.getId()).orElse(null);
            Post post = postRepository.findById(dto.getPostId()).orElse(null);

            new_reply.setUser(user);
            new_reply.setPost(post);

            post.getReplies().add(new_reply);
            replyRepository.save(new_reply);
            setSuccessResult(reply_result_dto);
            reply_result_dto.setCode(1);
        }else{
            logger.info("[Reply_write] 댓글 수정 로직 동작.");
            check_reply.setComment(dto.getComment());

            replyRepository.flush();
            setSuccessResult(reply_result_dto);
            reply_result_dto.setCode(2);
        }

        return reply_result_dto;
    }

    @Transactional
    public ReplyDeleteResultDto reply_delete(Long PostId, Long UserId, Long ReplyId)
    {
        ReplyDeleteResultDto replyDeleteResultDto = new ReplyDeleteResultDto();
        replyDeleteResultDto.setUserId(UserId);
        replyDeleteResultDto.setReplyId(ReplyId);

        logger.info("[reply_delete] 댓글 삭제 로직 동작. postId:{}, userId:{}",PostId,UserId);
        Reply reply = replyRepository.findById(ReplyId).orElse(null);

        if(!UserId.equals(reply.getUser().getId()))
        {
            logger.info("[reply_delete] 사용자가 삭제할수 없는 댓글입니다");
            setFailResult(replyDeleteResultDto);
            replyDeleteResultDto.setMsg("사용자가 삭제할수 없는 댓글입니다");
            return replyDeleteResultDto;
        }
        else {
            replyRepository.deleteByPost_PostIdAndUser_Id(PostId,UserId);
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
