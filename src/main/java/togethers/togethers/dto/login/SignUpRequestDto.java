package togethers.togethers.dto.login;

import lombok.*;
import togethers.togethers.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {


    private String id;

    private String password;

    private String name;

    private String role;

    private String nickname;

    private String email;
    private String domain;

    private Date birth;

    private String phoneNum;



}
