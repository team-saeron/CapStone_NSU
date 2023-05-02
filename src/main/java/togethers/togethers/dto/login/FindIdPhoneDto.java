package togethers.togethers.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindIdPhoneDto {
    String name;
    String phoneNum;

    public FindIdPhoneDto(String name, String phoneNum){
        this.name=name;
        this.phoneNum=phoneNum;
    }
}