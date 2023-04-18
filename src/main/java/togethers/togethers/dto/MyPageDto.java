package togethers.togethers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto
{
    private String nickname;
    private String Uid;

    public MyPageDto(String nickname, String uid) {
        this.nickname = nickname;
        Uid = uid;
    }
}
