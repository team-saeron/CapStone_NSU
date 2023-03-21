package togethers.togethers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageUpdateDto {
    String password;

    public void MyPageUpdateDto(String password, String checkPassword){
        this.password = password;
    }

}
