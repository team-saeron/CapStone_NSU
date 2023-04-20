package togethers.togethers.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PasswordUpdatedDto {
    public PasswordUpdatedDto() {}

    private String Uid;
    private String originPassword;
    private String newPassword;
    private String newPasswordCheck;





}
