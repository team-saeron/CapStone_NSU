package togethers.togethers.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
    private boolean rememberEmail;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String password;

    private String phoneNum;

    private Date birth;



}
