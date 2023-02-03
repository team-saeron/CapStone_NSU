package togethers.togethers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import togethers.togethers.entity.User;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SignUpRequestDto {

    private String id;

    private String password;

    private String name;

    private String role;

    private String nickname;

    private String email;

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
