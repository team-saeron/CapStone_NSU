package togethers.togethers.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@Table(name = "member")
public class Member {

    @Id
    @Column(name="member_id")
    private String id;
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
    @Column(name="member_name")
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @Column(name="member_pw")
    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String password;
    private String nickname;
    @Column(name="phone_num")
    private String phoneNum;

    private Date birth;



}
