package togethers.togethers.dto;

import lombok.*;
import togethers.togethers.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    public SignUpRequestDto(HttpServletRequest request)
    {
        this.id = request.getParameter("id");
        this.password = request.getParameter("password");
        this.email = request.getParameter("email");
        this.name  = request.getParameter("name");
        this.nickname = request.getParameter("nickname");
        this.phoneNum = request.getParameter("phoneNum");
        this.role = "user";
    }

    private String id;

    private String password;

    private String name;

    private String role;

    private String nickname;

    private String email;
    private String domain;

//    private Date birth;

    private String phoneNum;

    //    public User toEntity(String encodedPassword){
//        return User.builder()
//                .uid(id)
//                .password(encodedPassword)
//                .name(name)
//                .role(role)
//                .nickname(nickname)
//                .email(email)
//                .birth(birth)
//                .phoneNum(phoneNum)
//                .build();
//    }

}
