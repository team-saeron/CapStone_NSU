package togethers.togethers.controller;

import lombok.Data;
import java.sql.Date;

@Data
public class MemberForm {

    private String id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phoneNum;

    private Date birth;



}
