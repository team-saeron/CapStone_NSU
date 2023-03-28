package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.dto.*;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;


@RestController
@Slf4j
public class PostController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;



    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping(value = "/post/write") //게시물 작성
    public PostUpResultDto PostWrite(    @RequestParam(value = "file") MultipartFile file,
                                         @RequestParam(value = "title")String title,
                                         @RequestParam(value = "mouthly")String mouthly,
                                         @RequestParam(value = "lease")String lease,
                                         @RequestParam(value = "text")String text,
                                         @RequestParam(value = "roomType")Boolean roomType,
                                         @RequestParam(value = "getType")Boolean getType)throws Exception
    {
        LOGGER.info("[PostController] PostController 동작");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        User user = (User)principal;
        LOGGER.info("User Uid:{}",user.getUid());

        PostUpRequestDto postUpRequestDto = PostUpRequestDto.builder()
                .title(title)
                .mounthly(mouthly)
                .lease(lease)
                .context(text)
                .getType(getType)
                .roomType(roomType)
                .build();



        PostUpResultDto postUpResultDto = postService.post_save(postUpRequestDto,file,user.getUid());

        return postUpResultDto;
    }

    @GetMapping(value = "/post/modify") //게시물 수정 클릭시 동작하는 로직.
    public String post_modify(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        Post post = user.getPost();
        model.addAttribute("post",post);

        return "post/modify";
    }


    @PatchMapping(value = "/post/modify") // /post/modify/{post_id}로변경, 게시물 수정 할시 동작하는 로직
    public PostUpResultDto post_modify(Long post_id,
                                       @RequestParam(value = "title")String title,
                                       @RequestParam(value = "mouthly")String mouthly,
                                       @RequestParam(value = "lease")String lease,
                                       @RequestParam(value = "text")String context,
                                       @RequestParam(value = "roomType")Boolean roomType,
                                       @RequestParam(value = "getType")Boolean getType)throws Exception //@PahtVariable로 변경

    {
        LOGGER.info("[post_modify] 게시물 수정 Controller 동작. post_id:{}",post_id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        PostEditRequestDto postEditRequestDto = PostEditRequestDto.builder()
                .title(title)
                .context(context)
                .lease(lease)
                .mounthly(mouthly)
                .getType(getType)
                .roomType(roomType)
                .build();

        PostUpResultDto postUpResultDto = postService.post_edit(post_id, user.getUid(), postEditRequestDto);

        return postUpResultDto;
    }


    @PostMapping(value = "/post/delete") // /post/delete/{PostId}
    public PostDeleteResultDto post_delete(Long PostId)
    {
        LOGGER.info("[post_delete] 게시물 삭제 컨트롤러 동작. postId: {}",PostId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        PostDeleteResultDto postDeleteResultDto = postService.post_delete(PostId, user.getUid());

        return postDeleteResultDto;
    }

//    @GetMapping(value = "/post/detailPost")
//    public void post_detailPost(Long PostId)
//    {
//        LOGGER.info("[post_detailPost] 게시물 세부사항 관련 로직 동작 PostId:{}",PostId);
//
//        Post post = postService.findPost(PostId);
//        RoomPicture photo = postService.findPhoto(PostId);
//        List<Reply> replies = postService.findReply(PostId);
//
//        DetailPostDto detailPostDto = postService.detail_post(post, photo, replies);
//
//        System.out.println("*************************************************************************");
//        System.out.println(detailPostDto.getTitle());
//        System.out.println(detailPostDto.getContext());
//        System.out.println(detailPostDto.getPhoto_path());
//        for (Reply reply : detailPostDto.getReplies()) {
//            System.out.println(reply.getComment());
//        }
//    }






    @ExceptionHandler(value=IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handle(IllegalStateException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "사용자가 이미 게시물 존재합니다");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping
    public void saveLike(Long id,LikeDto likeDto){
        postService.saveLike(id, likeDto);
    }
}

