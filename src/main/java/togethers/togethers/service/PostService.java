package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.config.CommonResponse;
import togethers.togethers.dto.*;
import togethers.togethers.dto.post.*;
import togethers.togethers.entity.*;
import togethers.togethers.repository.*;
//import togethers.togethers.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final FavoriteRepository favoriteRepository;

    private final NotificationRepository notificationRepository;

    private final AwsFileUrlRepository awsFileUrlRepository;

    private final AwsS3Service awsS3Service;

    @Transactional
    public Post findPost(Long postId)
    {
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional
    public List<Reply>findReply(Long postId)
    {
        List<Reply> replies = replyRepository.findAllByPost_PostId(postId);
        return replies;
    }



    @Transactional
    public List<AwsFileUrl>findAwsUrl(Long postId)
    {
        List<AwsFileUrl> awsFileUrls = awsFileUrlRepository.findAllByPost_PostId(postId);
        return awsFileUrls;
    }



    @Transactional
    public boolean checkFavorite(Long postId,Long userId) {
        Favorite favorite = favoriteRepository.findByPost_PostIdAndUser_Id(postId, userId).orElse(null);
        logger.info("[checkFavorite] 해당 게시물 좋아요 여부 확인 Service 동작 postId :{}, userId:{}",postId,userId);

        boolean check;
        if (favorite != null) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    @Transactional
    public List<RecentlyPostDto>RecentlyPost()
    {
        logger.info("[RecentlyPost] 최근 게시물 조회 로직 Service 동작.");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dtoDate = new String();
        List<RecentlyPostDto>recentlyPostDto = new ArrayList<>();

        List<Post> postList = postRepository.findTop5ByOrderByPublishDateDesc();

        for (Post post : postList) {
            dtoDate = simpleDateFormat.format(post.getPublishDate());

            RecentlyPostDto build = RecentlyPostDto
                    .builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .date(dtoDate)
                    .build();

            recentlyPostDto.add(build);
        }

        return recentlyPostDto;
    }

    @Transactional
    public Long MonthlyPostWrite(MonthlyPostRequestDto dto, List<MultipartFile> files, String uid) throws Exception
    {
        logger.info("[MonthlyPostWrite] 월세 게시물 작성 Service 로직 동작 userId:{} 게시물 제목: {} 이미지 갯수 : {}",uid,dto.getTitle(),files.size());

        User user = userRepository.findByUid(uid).orElse(null);

        Post post = new Post(dto);
        post.setUser(user);
        postRepository.save(post);

        user.setPost(post);
        userRepository.flush();

        awsS3Service.upload(post.getPostId(), files);
        return post.getPostId();

    }

    @Transactional
    public Long LeasePostWrite(LeasePostRequestDto dto, List<MultipartFile> files, String uid) throws Exception
    {
        logger.info("[MonthlyPostWrite] 전세 게시물 작성 Service 로직 동작 userId:{} 게시물 제목: {}, 이미지 갯수 : {}",uid,dto.getTitle(),files.size());
        User user = userRepository.findByUid(uid).orElse(null);

        Post post = new Post(dto);
        post.setUser(user);
        postRepository.save(post);

        user.setPost(post);
        userRepository.flush();

        awsS3Service.upload(post.getPostId(), files);

        return post.getPostId();
    }

    @Transactional
    public PostUpResultDto postEdit(Long postId, String uid , PostEditRequestDto postEditRequestDto)
    {
        logger.info("[post_edit] 게시물 수정 로직 동작 post_id:{}, Uid:{} ",postId,uid);
        PostUpResultDto postUpResultDto = new PostUpResultDto();

        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findByUid(uid).orElse(null);
        Long tempPostId = post.getPostId();
        if(!user.getPost().getPostId().equals(post.getPostId()))
        {
            setFailResult(postUpResultDto);
            postUpResultDto.setMsg("사용자가 권한이 존재하지 않습니다");
            return postUpResultDto;
        }

        post.PostEdit(postEditRequestDto);

        postRepository.flush();

        if(post.getPostId().equals(tempPostId)){

            postUpResultDto.setContext(postEditRequestDto.getContext());
            postUpResultDto.setTitle(postEditRequestDto.getTitle());
            setSuccessResult(postUpResultDto);
        }else {
            setFailResult(postUpResultDto);
        }

        return postUpResultDto;
    }


    @Transactional
    public PostDeleteResultDto postDelete(Long postId, String uid)
    {
        logger.info("[post_delete] 게시물 삭제 로직 동작. postId:{} userId:{}",postId,uid);
        User user = userRepository.findByUid(uid).orElse(null);
        PostDeleteResultDto postDeleteResultDto = new PostDeleteResultDto();
        postDeleteResultDto.setPostId(postId);


        if(user.getPost()==null||user.getPost().getPostId()!=postId)
        {
            logger.info("[post_delete] 사용자가 게시물이 없거나 사용자가 삭제할수 없는 게시물.");
            setFailResult(postDeleteResultDto);
            postDeleteResultDto.setMsg("사용자가 권한이 존재하지 않습니다");

            return postDeleteResultDto;
        }

        postRepository.deleteBypostId(postId);

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
    public DetailPostDto detailPost(Post post)
    {

        User user = post.getUser();

        logger.info("[detail_post] 게시물 세부사항 서비스 로직 동작 PostId:{},user Uid:{}",post.getPostId(),user.getUid());
        DetailPostDto detailPostDto = DetailPostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .context(post.getContext())
                .monthly(post.getMonthly())
                .lease(post.getDeposit())
                .maintenanceCost(post.getManagement_fee())
                .userId(user.getId())
                .area(post.getArea())
                .Uid(user.getUid())
                .build();
        return detailPostDto;
    }

    @Transactional
    public Page<Post> postPostList(Pageable pageable)
    {
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);

        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));
        return postRepository.findAll(pageRequest);
    }

    @Transactional
    public Page<Post> searchPost(String keyword, Pageable pageable)
    {
        logger.info("[SearchPost] 게시물 검색 Service 로직 동작. keyword: {}",keyword);
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));
        return postRepository.findByTitleContaining(keyword,pageRequest);

    }

    @Transactional
    public Page<Post> searchPostUsingCategory(String areaName, Pageable pageable)
    {
        logger.info("[SearchPostUsingCategory] 카테고리에서 클릭한 지역이름을 이용해 게시물 조회 Service로직 동작 지역 이름: {}",areaName);
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "postId"));

        return postRepository.findByAreaContaining(areaName,pageRequest);
    }


    @Transactional
    public boolean saveLike(Long userId, Long postId){

        logger.info("[saveLike] 게시물 좋아요 Service 로직 동작. 유저 Id:{}, 게시물 pk:{}",userId, postId);
        boolean check = true;

        User user = userRepository.findById(userId).orElse(null);
        User postUser = userRepository.findByPost_PostId(postId).orElse(null);

        Favorite checkFavorite = favoriteRepository.findByPost_PostIdAndUser_Id(postId,user.getId()).orElse(null);
        List<Favorite> all = favoriteRepository.findAllByUser_Id(user.getId());
        System.out.println("마몽 : " + all.size());;

        if(checkFavorite == null)
        {

            Post post = postRepository.findById(postId).orElse(null);
            Favorite favorite = new Favorite();
            favorite.setPost(post);
            favorite.setUser(user);
            favorite.setMyFavorite(true);
            favoriteRepository.save(favorite);

            /**게시물 작성자에게 알림**/
            Notification notification = Notification.builder()
                    .title("좋아요 알림")
                    .user(postUser)
                    .message(user.getNickname() + "님이 회원님의 게시물을 좋아합니다")
                    .created(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);

        }else{
            logger.info("[saveLike] 게시물 좋아요 취소 로직 동작 좋아요의 게시물:{} 유저 pk:{}",checkFavorite.getPost().getPostId(),checkFavorite.getUser().getId());
            favoriteRepository.delete(checkFavorite);
            favoriteRepository.flush();
            check = false;
        }
        return check;
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
