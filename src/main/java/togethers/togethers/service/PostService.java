package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.dto.*;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.ReplyRepository;
import togethers.togethers.repository.RoompictureRepository;
//import togethers.togethers.repository.UserRepository;
import togethers.togethers.repository.UserRepository;

import java.awt.geom.Area;
import java.io.File;
import java.util.List;
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

    @Autowired
    private final ReplyRepository replyRepository;

    @Transactional
    public Post findPost(Long post_id)
    {
        return postRepository.findById(post_id).orElse(null);
    }

    @Transactional
    public List<Reply>findReply(Long PostId)
    {
        List<Reply> replies = replyRepository.findAllByPost_PostId(PostId);
        return replies;
    }

    @Transactional
    public RoomPicture findPhoto(Long PostId)
    {
        RoomPicture roomPicture = roompictureRepository.findByPost_PostId(PostId).orElse(null);
        return roomPicture;
    }


    @Transactional(readOnly = false)
    public PostUpResultDto post_save(PostUpRequestDto postUpRequestDto,
                                     MultipartFile file,String Uid) throws Exception {
        User user = userRepository.findByUid(Uid).orElse(null);
        logger.info("[post_save] 게시물 저장 로직 시작. id : {}", user.getUid());

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

            photo_save(post.getPostId(),file);

            postUpResultDto = PostUpResultDto.builder()
                    .title(post.getTitle())
                    .context(post.getTitle())
                    .build();


            if (post.getPostId() == user.getPost().getPostId()) // post와 user,Roompicture pk 검증
            {
                setSuccessResult(postUpResultDto);
            } else {
                setFailResult(postUpResultDto);
            }
        }

        return postUpResultDto;
    }

    @Transactional
    public PostUpResultDto MonthlyPostWrite(MonthlyPostRequestDto dto,MultipartFile file,String Uid) throws Exception
    {
        logger.info("[MonthlyPostWrite] 월세 게시물 작성 Service 로직 동작 userId:{} 게시물 제목: {}",Uid,dto.getTitle());

        User user = userRepository.findByUid(Uid).orElse(null);
        PostUpResultDto postUpResultDto;

        if(user.getPost()!=null)
        {
            logger.info("[post_save] 사용자가 이미 게시물이 존재함. id: {}", user.getUid());
            throw new IllegalStateException();
        }else {
            Post post = new Post(dto);
            post.setUser(user);
            postRepository.save(post);

            user.setPost(post);
            userRepository.flush();

            photo_save(post.getPostId(),file);

            postUpResultDto = PostUpResultDto.builder()
                    .title(post.getTitle())
                    .context(post.getTitle())
                    .build();
            if (post.getPostId() == user.getPost().getPostId()) // post와 user,Roompicture pk 검증
            {
                setSuccessResult(postUpResultDto);
            } else {
                setFailResult(postUpResultDto);
            }
        }
        return postUpResultDto;
    }

    @Transactional
    public PostUpResultDto LeasePostWrite(LeasePostRequestDto dto,MultipartFile file,String Uid) throws Exception
    {
        logger.info("[MonthlyPostWrite] 전세 게시물 작성 Service 로직 동작 userId:{} 게시물 제목: {}",Uid,dto.getTitle());

        User user = userRepository.findByUid(Uid).orElse(null);
        PostUpResultDto postUpResultDto;

        if(user.getPost()!=null)
        {
            logger.info("[post_save] 사용자가 이미 게시물이 존재함. id: {}", user.getUid());
            throw new IllegalStateException();
        }else {
            Post post = new Post(dto);
            post.setUser(user);
            postRepository.save(post);

            user.setPost(post);
            userRepository.flush();

            photo_save(post.getPostId(),file);

            postUpResultDto = PostUpResultDto.builder()
                    .title(post.getTitle())
                    .context(post.getTitle())
                    .build();
            if (post.getPostId() == user.getPost().getPostId()) // post와 user,Roompicture pk 검증
            {
                setSuccessResult(postUpResultDto);
            } else {
                setFailResult(postUpResultDto);
            }
        }
        return postUpResultDto;
    }

    @Transactional
    public PostUpResultDto post_edit(Long post_id,String Uid ,PostEditRequestDto postEditRequestDto)
    {
        logger.info("[post_edit] 게시물 수정 로직 동작 post_id:{}, Uid:{} ",post_id,Uid);
        PostUpResultDto postUpResultDto = new PostUpResultDto();

        Post post = postRepository.findById(post_id).orElse(null);
        User user = userRepository.findByUid(Uid).orElse(null);
        Long tempPost_id = post.getPostId();
        if(!user.getPost().getPostId().equals(post.getPostId()))
        {
            setFailResult(postUpResultDto);
            postUpResultDto.setMsg("사용자가 권한이 존재하지 않습니다");
            return postUpResultDto;
        }

        post.PostEdit(postEditRequestDto);

        postRepository.flush();

        if(post.getPostId().equals(tempPost_id)){

            postUpResultDto.setContext(postEditRequestDto.getContext());
            postUpResultDto.setTitle(postEditRequestDto.getTitle());
            setSuccessResult(postUpResultDto);
        }else {
            setFailResult(postUpResultDto);
        }

        return postUpResultDto;
    }


    @Transactional
    public PostDeleteResultDto post_delete(Long PostId,String Uid)
    {
        logger.info("[post_delete] 게시물 삭제 로직 동작. postId:{} userId:{}",PostId,Uid);
        User user = userRepository.findByUid(Uid).orElse(null);
        PostDeleteResultDto postDeleteResultDto = new PostDeleteResultDto();
        postDeleteResultDto.setPostId(PostId);


        if(user.getPost()==null||user.getPost().getPostId()!=PostId)
        {
            logger.info("[post_delete] 사용자가 게시물이 없거나 사용자가 삭제할수 없는 게시물.");
            setFailResult(postDeleteResultDto);
            postDeleteResultDto.setMsg("사용자가 권한이 존재하지 않습니다");

            return postDeleteResultDto;
        }

        postRepository.deleteBypostId(PostId);

        user.setPost(null);
        userRepository.flush();

        if(user.getPost()==null)
        {
            setSuccessResult(postDeleteResultDto);
        }else{
            setFailResult(postDeleteResultDto);
        }

        return postDeleteResultDto;
    }


    @Transactional
    public DetailPostDto detail_post(Post post, RoomPicture photo)
    {

        User user = post.getUser();

        logger.info("[detail_post] 게시물 세부사항 서비스 로직 동작 PostId:{},user Uid",post.getPostId(),user.getUid());
        DetailPostDto detailPostDto = DetailPostDto.builder()
                .title(post.getTitle())
                .context(post.getContext())
                .mounthly(post.getMonthly())
                .lease(post.getDeposit())
                .userId(user.getId())
                .area(post.getArea())
                .Uid(user.getUid())
                .photo_name(photo.getFilename())
                .photo_path(photo.getFilepath())
                .build();

        return detailPostDto;
    }

    @Transactional
    public Page<Post> post_postList(Pageable pageable)
    {
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);

        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));
        return postRepository.findAll(pageRequest);
    }




    public Long photo_save(Long post_id, MultipartFile file)throws Exception //이미지 저장로직
    {
        logger.info("[Photo_save] 이미지 저장로직 동작 post_id:{}, 사진 제목:{}",post_id,file.getOriginalFilename());


        Post post = postRepository.findById(post_id).orElse(null);

        RoomPicture roomPicture = new RoomPicture();
        String projectPath =  System.getProperty("user.dir")+"/src/main/resources/static/files";
        //window는 경로를 //src//main//이렇게 슬래쉬 2개 필요.

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        roomPicture.setFilename(fileName);
        post.setFileName(fileName);
        roomPicture.setFilepath("/files/" + fileName);
        roomPicture.setPost(post);

        postRepository.flush();
        roompictureRepository.save(roomPicture);

        return roomPicture.getId();
    }


    public Page<Post>SearchPost(String keyword,Pageable pageable)
    {
        logger.info("[SearchPost] 게시물 검색 Service 로직 동작. keyword: {}",keyword);
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));
        return postRepository.findByTitleContaining(keyword,pageRequest);

    }


    public Page<Post>SearchPostUsingCategory(String areaName,Pageable pageable)
    {
        logger.info("[SearchPostUsingCategory] 카테고리에서 클릭한 지역이름을 이용해 게시물 조회 Service로직 동작 지역 이름: {}",areaName);
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));

        return postRepository.findByAreaContaining(areaName,pageRequest);
    }













    private void setSuccessResult (BaseResultDto result)
    {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult (BaseResultDto result)
    {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
