package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.*;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;

import java.util.List;

@Controller
public class PostController {


    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }
    Logger logger = LoggerFactory.getLogger(PostController.class);


    @GetMapping(value = "/chooseType")
    public String chooseType()
    {
        logger.info("[chooseType] GET 게시물 작성하기전 게시물 월/전세 타입 선택 동작");
        return "chooseType";
    }


    /**월세 타입의 게시물 GET,POST MAPPING **/
    @GetMapping(value = "/writeMonth")
    public String postWriteMouth(Model model)
    {
        logger.info("[postWriteMouth] GET 게시물 월세 작성 Controller 동작.");
        MonthlyPostRequestDto monthlyPostRequestDto = new MonthlyPostRequestDto();

        model.addAttribute("Dto",monthlyPostRequestDto);
        model.addAttribute("areaEnum",AreaEnum.values());

        return "writeMonth";
    }

    @PostMapping(value = "/writeMonth")
    public String postWriteMouth(MonthlyPostRequestDto dto, MultipartFile file) throws Exception
    {
        logger.info("[postWriteMouth] POST 월세타입의 게시물 사진 업로드 동작");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        PostUpResultDto postUpResultDto = postService.MonthlyPostWrite(dto, file, user.getUid());

        logger.info("[postWriteMouth] ResultDto 결과: {}, {}, {}",postUpResultDto.getCode(),postUpResultDto.getMsg(),postUpResultDto.getContext());
        return "redirect:/";
    }



    /**전세 타입의 게시물 GET, POST 매핑**/
    @GetMapping("/writeBeforePay")
    public String writeBeforePay(Model model){

        logger.info("[postWriteMouth] GET 게시물 전세 작성 Controller 동작.");
        LeasePostRequestDto leasePostRequestDto = new LeasePostRequestDto();
        model.addAttribute("dto",leasePostRequestDto);
        model.addAttribute("areaEnum",AreaEnum.values());
        return "writeBeforePay";
    } // 전세 타입의 post

    @PostMapping("/writeBeforePay")
    public String writeBeforePay(LeasePostRequestDto dto,MultipartFile file) throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        logger.info("[postWriteMouth] POST 게시물 전세 작성 Controller 동작.");

        PostUpResultDto postUpResultDto = postService.LeasePostWrite(dto, file, user.getUid());

        logger.info("[postWriteMouth] ResultDto 결과: {}, {}, {}",postUpResultDto.getCode(),postUpResultDto.getMsg(),postUpResultDto.getContext());
        return "redirect:/";

    }


    @GetMapping(value = "post/postList")
    public String post_postList(@PageableDefault Pageable pageable, Model model){
        logger.info("[postList]게시물 목록 조회 controller 동작");

        Page<Post>postList = postService.post_postList(pageable);

        model.addAttribute("postList",postList);
        logger.info("[postList] 총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                postList.getTotalElements(), postList.getTotalPages(), postList.getSize(),
                postList.getNumber(), postList.getNumberOfElements());

        return "post/postList";

    }

    @GetMapping(value = "/post/detailPost/{postId}")
    public String post_detailPost(@PathVariable("postId")Long PostId, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;


        logger.info("[post_detailPost] 게시물 세부사항 관련 로직 동작 PostId:{},userId: {}",PostId,user.getUid());

        Post post = postService.findPost(PostId);
        RoomPicture photo = postService.findPhoto(PostId);
        List<Reply> replies = postService.findReply(PostId);

        DetailPostDto detailPostDto = postService.detail_post(post, photo);


        model.addAttribute("post",detailPostDto);
        model.addAttribute("user",user);
        model.addAttribute("replies",replies);
        model.addAttribute("postId",PostId);


        return "post/detailPost";
    }
}
