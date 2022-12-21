package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    @GetMapping("/postlist")
    public String postlist()
    {
        return "post/postlist";
    }


    @GetMapping("/detailPost")
    public String detailPost()
    {
        return "post/detailPost";
    }
}