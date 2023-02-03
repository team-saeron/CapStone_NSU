package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.dto.PostUpRequestDto;
import togethers.togethers.dto.PostUpResultDto;
import togethers.togethers.dto.SignUpResultDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.RoompictureRepository;
//import togethers.togethers.repository.UserRepository;
import togethers.togethers.repository.UserRepository;

import java.io.File;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final RoompictureRepository roompictureRepository;

    @Transactional(readOnly = false)
    public PostUpResultDto post_save(PostUpRequestDto postUpRequestDto,
                                     MultipartFile file,String name) throws Exception {
        logger.info("[post_save] 게시물 저장 로직 시작. id : {}", name);

        User user = userRepository.getByUid(name).orElse(null);
        PostUpResultDto postUpResultDto;



        if (user.getPost() != null) //사용자가 게시물이 이미 있을경우 예외처리
        {
            logger.info("[post_save] 사용자가 이미 게시물이 존재함. id: {}", user.getUid());
            throw new IllegalStateException();
        } else {

            Post post = new Post(postUpRequestDto); //html에서 받아온 dto로 post 생성
            post.setUser(user);
            postRepository.save(post);


            user.setPost(post);
            userRepository.save(user);


            RoomPicture roomPicture = new RoomPicture();
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            file.transferTo(saveFile);

            roomPicture.setFilename(fileName);
            roomPicture.setFilepath("/files/" + fileName);
            roomPicture.setPost(post);

            roompictureRepository.save(roomPicture);

             postUpResultDto = PostUpResultDto.builder()
                    .title(post.getTitle())
                    .context(post.getTitle())
                    .build();


            if (post.getPost_id() == user.getPost().getPost_id() && post.getPost_id() == roomPicture.getPost().getPost_id()) // post와 user,Roompicture pk 검증
            {
                setSuccessResult(postUpResultDto);
            } else {
                setFailResult(postUpResultDto);
            }
        }

            return postUpResultDto;
        }






        private void setSuccessResult (SignUpResultDto result)
        {
            result.setSuccess(true);
            result.setCode(CommonResponse.SUCCESS.getCode());
            result.setMsg(CommonResponse.SUCCESS.getMsg());
        }

        private void setFailResult (SignUpResultDto result)
        {
            result.setSuccess(false);
            result.setCode(CommonResponse.FAIL.getCode());
            result.setMsg(CommonResponse.FAIL.getMsg());
        }

}
