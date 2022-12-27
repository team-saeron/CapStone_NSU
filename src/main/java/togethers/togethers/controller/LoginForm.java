package togethers.togethers.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class LoginForm {
    private String id;
    private String password;

}
