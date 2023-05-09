package togethers.togethers.social;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginDto {
    private String nickname;
    private String email;
    private String password;


}
