package togethers.togethers.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@NoArgsConstructor
public class MemberForm {

    private String id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phoneNum;
    private Date birth;

//    @Builder
//    public MemberForm(String name, String email, String nickname, String phoneNum, Date birth) {
//        this.name = name;
//        this.email = email;
//        this.nickname = nickname;
//        this.phoneNum = phoneNum;
//        this.birth = birth;
//    }

}
