package togethers.togethers.service.form;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
public class MemberForm {

    private String uid;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phoneNum;
    private Date birth;



}