package togethers.togethers.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDto {
    private String id;

    private String pw;

    public SignInRequestDto(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
