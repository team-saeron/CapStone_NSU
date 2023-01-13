package togethers.togethers.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@NoArgsConstructor
public class MemberForm {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String id;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNum;
    @NotBlank(message = "생년월인은 필수 입력 값입니다.")
    private Date birth;

    @Builder
    public MemberForm(String id, String password, String name, String email, String nickname, String phoneNum, Date birth) {
        this.name = name;
        this.id=id;
        this.email = email;
        this.password=password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.birth = birth;
    }

}
