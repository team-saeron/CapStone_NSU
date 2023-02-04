package togethers.togethers.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.dto.PostUpRequestDto;
import togethers.togethers.dto.PostUpResultDto;
import togethers.togethers.entity.User;
import togethers.togethers.service.PostService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PostController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/write")
    public PostUpResultDto PostWrite(@Valid PostUpRequestDto postUpRequestDto, MultipartFile file) throws Exception
    {
        LOGGER.info("[PostController] PostController 동작");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        User user = (User)principal;
        LOGGER.info("User Uid:{}",user.getUid());

        PostUpResultDto postUpResultDto = postService.post_save(postUpRequestDto, file, user.getUid());

        return postUpResultDto;
    }


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
}