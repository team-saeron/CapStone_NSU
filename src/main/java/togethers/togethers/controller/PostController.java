package togethers.togethers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import togethers.togethers.Enum.AreaEnum;
import togethers.togethers.dto.post.DetailPostDto;
import togethers.togethers.dto.post.LeasePostRequestDto;
import togethers.togethers.dto.post.MonthlyPostRequestDto;
import togethers.togethers.dto.post.PostSearchDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;
import togethers.togethers.service.UserService;

import java.util.List;



@Controller
public class PostController {



    private final PostService postService;
    private final UserService userService;
    private AreaEnum[] area = AreaEnum.values();

    @Autowired
    public PostController(PostService postService, UserService userService)
    {
        this.postService = postService;
        this.userService = userService;
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
    public String postWriteMouth(Model model,RedirectAttributes attr)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal =="anonymousUser"){
            logger.info("[postWriteMouth] 사용자가 로그인 하지 않아 게시물 작성 불가");

            attr.addFlashAttribute("msg","로그인 이후 게시물 작성이 가능합니다");
            return "redirect:/";
        }else {
            User user = (User)principal;
            if(user.getPost() != null){
                logger.info("[postWriteMouth] 사용자 게시물 이미 존재합니다");

                attr.addFlashAttribute("already_post","이미 게시물이 존재합니다.");
                return "redirect:/";
            }else{
                logger.info("[postWriteMouth] GET 게시물 월세 작성 Controller 동작.");
                MonthlyPostRequestDto monthlyPostRequestDto = new MonthlyPostRequestDto();
                model.addAttribute("dto",monthlyPostRequestDto);
                model.addAttribute("areaEnum",AreaEnum.values());
                return "writeMonth";
            }
        }
    }

    @PostMapping(value = "/writeMonth")
    public String postWriteMouth(MonthlyPostRequestDto dto, MultipartHttpServletRequest req) throws Exception
    {
        logger.info("[postWriteMouth] POST 월세타입의 게시물 사진 업로드 동작");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;
        List<MultipartFile> files = req.getFiles("btnAtt");

        Long postId = postService.MonthlyPostWrite(dto, files, user.getUid());

        return "redirect:/";
    }



    /**전세 타입의 게시물 GET, POST 매핑**/
    @GetMapping("/writeBeforePay")
    public String writeBeforePay(Model model,RedirectAttributes attr){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal =="anonymousUser"){
            logger.info("[postWriteMouth] 사용자가 로그인 하지 않아 게시물 작성 불가");
            attr.addFlashAttribute("msg","로그인 이후 게시물 작성이 가능합니다");
            return "redirect:/";
        }else {
            User user = (User)principal;

            if(user.getPost() != null){
                logger.info("[postWriteMouth] 사용자 게시물 이미 존재합니다");

                attr.addFlashAttribute("already_post","이미 게시물이 존재합니다.");
                return "redirect:/";
            }
            logger.info("[postWriteMouth] GET 게시물 전세 작성 Controller 동작.");
            LeasePostRequestDto leasePostRequestDto = new LeasePostRequestDto();
            model.addAttribute("dto",leasePostRequestDto);
            model.addAttribute("areaEnum",AreaEnum.values());
            return "writeBeforePay";
        }
    } // 전세 타입의 post

    @PostMapping("/writeBeforePay")
    public String writeBeforePay(LeasePostRequestDto dto,MultipartHttpServletRequest req) throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User)principal;

        List<MultipartFile> files = req.getFiles("btnAtt");
        logger.info("[postWriteMouth] POST 게시물 전세 작성 Controller 동작.");

        Long postId = postService.LeasePostWrite(dto, files, user.getUid());

        return "redirect:/";

    }


    @GetMapping(value = "post/postList")
    public String post_postList(@PageableDefault Pageable pageable, Model model){
        logger.info("[postList]게시물 목록 조회 controller 동작");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal == "anonymousUser"){
            model.addAttribute("login_inform",false);
        }else{
            model.addAttribute("login_inform",true);
        }


        Page<Post>postList = postService.postPostList(pageable);

        model.addAttribute("postList",postList);
        model.addAttribute("category",AreaEnum.values());
        logger.info("[postList] 총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                postList.getTotalElements(), postList.getTotalPages(), postList.getSize(),
                postList.getNumber(), postList.getNumberOfElements());

        return "post/postList";

    }

    @GetMapping(value = "/post/detailPost/{postId}")
    public String post_detailPost(@PathVariable("postId")Long postId, Model model, RedirectAttributes attr)
    {
        if (postId == 0)
        {
            attr.addFlashAttribute("no_post","작성하신 게시물이 없습니다");
            return "redirect:/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        boolean check;
        String userNickname = new String();

        if(principal =="anonymousUser"){
            logger.info("[post_detailPost] 게시물 세부사항 로직 동작. 사용자 로그인 하지 않음 postId :{}",postId);
            check = false;
            userNickname = "";
            model.addAttribute("user_nickname",userNickname);
            model.addAttribute("check",check);
            model.addAttribute("login_inform",false);

        }else {
            User user = (User)principal;
            userNickname = user.getNickname();
            logger.info("[post_detailPost] 게시물 세부사항 로직 동작. 사용자 로그인 완료 postId :{}, userId : {}",postId,userNickname);
            check = postService.checkFavorite(postId, user.getId());
            model.addAttribute("user_nickname",userNickname);
            model.addAttribute("check",check);
            model.addAttribute("login_inform",true);

        }


        Post post = postService.findPost(postId);
        List<RoomPicture> images = postService.findPhoto(postId);
        List<Reply> replies = postService.findReply(postId);
        DetailPostDto detailPostDto = postService.detailPost(post);
        User writer = userService.findPostByPostId(postId);



        model.addAttribute("post",detailPostDto);
        model.addAttribute("replies",replies);
        model.addAttribute("postId",postId);
        model.addAttribute("category",AreaEnum.values());
        model.addAttribute("images",images);
        model.addAttribute("writer",writer);

        return "post/detailPost";
    }



    /**게시물 검색 관련 로직**/
    @PostMapping(value = "/post/search")
    public String SearchPost(@PageableDefault Pageable pageable, Model model, PostSearchDto dto){

        logger.info("[SearchPost] 게시물 검색 Controller 동작. keyword : {}",dto.getKeyword());
        Page<Post> posts = postService.searchPost(dto.getKeyword(), pageable);

        logger.info("[SearchPost] 게시물 검색 결과 갯수: {}",posts.getTotalElements());

        model.addAttribute("postList",posts);
        return "post/searchList";

    }

    @GetMapping(value = "/detailPost/Like")
    public String saveLike(@RequestParam("postId")Long postId,RedirectAttributes attr)
    {

        logger.info("[saveLike] 게시물 좋아요 Controller동작. postId:{}",postId);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser")
        {
            logger.info("[saveLike] 사용자가 로그인 하지 않아 게시물 좋아요 기능 동작 불가능");
            attr.addFlashAttribute("like_msg","로그인 이후 이용하여 주세요");


        }else{
            User user = (User)principal;
            logger.info("[saveLike] 게시물 좋아요 로직 동작. postId : {}, userId:{}",postId,user.getUid());
            postService.saveLike(user.getId(), postId);
        }
        return "redirect:/post/detailPost/"+postId;

    }

    @GetMapping(value = "/post/searchList")
    public String SearchPostUsingCategory(@RequestParam(value = "areaId",required = false,defaultValue = "")String areaId,
                                          @PageableDefault Pageable pageable, Model model)
    {
        int aid = 0;
        if(!areaId.equals("")){
             aid = Integer.parseInt(areaId);
        }

        logger.info("[SearchPost]홈페이지에서 사용자가 카테고리 클릭후 해당 게시물 검색. areaID:{}",aid);


        String areaName = area[aid].toString();
        logger.info("[findArea] Service 에서 사용자가 클릭한 지역게시물 Enum에서 조회. 결과: {}",areaName);


        Page<Post> posts = postService.searchPostUsingCategory(areaName, pageable);
        logger.info("[SearchPostUsingCategory] 카테고리 클릭후 해당지역 조회 게시물 결과. 지역이름:{} 게시물 갯수:{}",areaName,posts.getTotalElements());



        model.addAttribute("AreaId",areaId);
        model.addAttribute("postList",posts);
        model.addAttribute("category",AreaEnum.values());

        return "post/searchList";
    }


}
