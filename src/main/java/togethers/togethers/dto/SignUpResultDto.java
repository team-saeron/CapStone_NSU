package togethers.togethers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpResultDto {
    private boolean success;

    private int code;

    private String msg;
}

//  비밀번호 찾기 post --> id,email name 3개로 ok면 이메일로 새로운 비번 전송 --> 새로운 비번을 db로 dpdate
// --> email에서 비밀번호 변경 url을 전송 (/user/editpassword, 이거 자체가 비밀번호 변경) --> 바로 비번 변경