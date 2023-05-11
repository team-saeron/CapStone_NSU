package togethers.togethers.dto.mypage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto
{
    private String nickname;
    private String Uid;

    private String socialName;

    public MyPageDto(String nickname, String uid,String socialName) {
        this.nickname = nickname;
        this.Uid = uid;
        this.socialName =  socialName;
    }
}
