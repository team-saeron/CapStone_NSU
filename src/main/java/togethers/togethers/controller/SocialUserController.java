package togethers.togethers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")

public class SocialUserController {
    @PostMapping("login")
    public ResponseEntity<String> writeReview (){
        return ResponseEntity.ok().body("token");

    }
}
