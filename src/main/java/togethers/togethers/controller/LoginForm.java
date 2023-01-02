package togethers.togethers.controller;

import lombok.Data;
import togethers.togethers.domain.Member;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
