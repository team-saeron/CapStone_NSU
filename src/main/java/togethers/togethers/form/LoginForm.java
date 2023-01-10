package togethers.togethers.form;

import lombok.Builder;
import lombok.Data;
import togethers.togethers.entity.Role;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

   private String loginId;
   private String pw;


}
