package togethers.togethers.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class SignInRequestDto {
    private String id;

    private String password;

    public SignInRequestDto(){};

    public SignInRequestDto(String id, String pw) {
        this.id = id;
        this.password = pw;
    }
}
